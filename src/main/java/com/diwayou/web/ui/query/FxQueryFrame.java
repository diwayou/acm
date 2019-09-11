package com.diwayou.web.ui.query;

import com.diwayou.web.config.AppConfig;
import com.diwayou.web.http.driver.AppThread;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.store.IndexFieldName;
import com.diwayou.web.store.IndexType;
import com.diwayou.web.store.QueryResult;
import com.google.common.collect.Lists;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.lucene.document.Document;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FxQueryFrame extends Stage {

    private static final Logger log = AppLog.getBrowser();

    private TextField textField;

    private ResultSearcher searcher;

    private int pageSize;

    private GridPane grid;

    private List<TableColumn<Doc, String>> columns;

    private Pagination pagination;

    public FxQueryFrame(int pageSize) {
        this.pageSize = pageSize;
        this.grid = new GridPane();

        initSearchField();

        pagination = new Pagination(0, 0);
        pagination.setPageFactory(pageNum -> {
            int curPageNum = pageNum + 1;

            try {
                QueryResult result = searcher.search(textField.getText(), curPageNum);

                if (curPageNum == 1) {
                    int pageCount = Math.round(result.getTotal() / (float) pageSize);
                    pagination.setPageCount(pageCount);
                }

                TableView<Doc> tableView = buildTableView(pageSize, buildItem(result));
                return tableView;
            } catch (Exception e) {
                log.log(Level.WARNING, "", e);
                return null;
            }
        });
        grid.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            pagination.setPrefSize(newValue.getWidth(), newValue.getHeight());
            pagination.autosize();
        });

        grid.add(pagination, 0, 1);

        setScene(new Scene(grid, 1024, 768));

        initSearcher(pageSize);
    }

    private TableView<Doc> buildTableView(int pageSize, ObservableList<Doc> items) {
        TableView<Doc> tableView = new TableView<>(items);

        tableView.fixedCellSizeProperty().bind(tableView.heightProperty().divide(pageSize));

        tableView.setOnMouseClicked(me -> {
            if (me.getClickCount() != 2) {
                return;
            }

            ObservableList<TablePosition> cells = tableView.getSelectionModel().getSelectedCells();
            if (cells.isEmpty()) {
                return;
            }

            TablePosition position = cells.get(0);
            // 点击图片
            if (position.getColumn() == 5) {
                Doc doc = tableView.getSelectionModel().getSelectedItem();
                if (!doc.getType().equalsIgnoreCase(IndexType.image.name())) {
                    return;
                }

                if (AppConfig.isSystemBrowser()) {
                    try {
                        Desktop.getDesktop().open(new File(doc.getContent().getText()));
                    } catch (IOException ex) {
                        log.log(Level.WARNING, "", ex);
                    }
                } else {
                    ImageView imageView = new ImageView(Path.of(doc.getContent().getText()).toUri().toString());
                    Group group = new Group(imageView);
                    Scene scene = new Scene(group);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.sizeToScene();
                    stage.show();
                }
            }
        });

        initColumns(tableView);
        return tableView;
    }

    private void initColumns(TableView<Doc> tableView) {
        columns = Lists.newArrayListWithCapacity(IndexFieldName.values().length);
        for (IndexFieldName fieldName : IndexFieldName.values()) {
            TableColumn<Doc, String> col = new TableColumn<>(fieldName.getDisplayName());
            col.setCellValueFactory(new PropertyValueFactory<>(fieldName.name()));

            if (fieldName.equals(IndexFieldName.parentUrl) || fieldName.equals(IndexFieldName.url)) {
                col.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
                col.setEditable(true);
            } else if (fieldName.equals(IndexFieldName.content)) {
                col.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
            } else if (fieldName.equals(IndexFieldName.image)) {
                col.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
            } else {
                col.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
            }

            columns.add(col);
        }
        tableView.getColumns().setAll(columns);
    }

    private void initSearchField() {
        textField = new TextField();
        textField.setOnAction(ae -> {
            AppThread.async(() -> {
                pagination.setCurrentPageIndex(0);
            });
        });
        grid.add(textField, 0, 0);
    }

    private void initSearcher(int pageSize) {
        try {
            this.searcher = new ResultSearcher(pageSize, (p, qr) -> {
            });
        } catch (IOException e) {
            log.log(Level.WARNING, "", e);
        }
    }

    private ObservableList<Doc> buildItem(QueryResult result) {
        ObservableList<Doc> tableDocs = FXCollections.observableArrayList();
        for (int row = 0; row < result.getDocs().size(); row++) {
            Doc tableRow = new Doc();

            Document doc = result.getDocs().get(row);
            setRow(doc, tableRow);

            tableDocs.add(tableRow);
        }

        return tableDocs;
    }

    private void setRow(Document doc, Doc row) {
        TextArea parentUrl = new TextArea(doc.get(IndexFieldName.parentUrl.name()));
        parentUrl.setEditable(false);
        parentUrl.setWrapText(true);
        row.setParentUrl(parentUrl);

        TextArea url = new TextArea(doc.get(IndexFieldName.url.name()));
        url.setEditable(false);
        url.setWrapText(true);
        row.setUrl(url);

        String type = doc.get(IndexFieldName.type.name());
        row.setType(type);

        String ext = doc.get(IndexFieldName.ext.name());
        row.setExt(ext);

        TextArea content = new TextArea(doc.get(IndexFieldName.content.name()));
        content.setEditable(false);
        row.setContent(content);

        if (IndexType.image.name().equalsIgnoreCase(type)) {
            ImageView imageView = new ImageView(Path.of(content.getText()).toUri().toString());
            imageView.setPreserveRatio(true);
            row.setImage(imageView);
        }
    }

    @Override
    public void close() {
        super.close();

        if (searcher != null) {
            try {
                searcher.close();
            } catch (IOException ignore) {
            }
        }
    }

    public static class Doc {
        private ObjectProperty<TextArea> parentUrl = new SimpleObjectProperty<>(this, IndexFieldName.parentUrl.name());
        private ObjectProperty<TextArea> url = new SimpleObjectProperty<>(this, IndexFieldName.url.name());
        private StringProperty type = new SimpleStringProperty(this, IndexFieldName.type.name());
        private StringProperty ext = new SimpleStringProperty(this, IndexFieldName.ext.name());
        private ObjectProperty<TextArea> content = new SimpleObjectProperty<>(this, IndexFieldName.content.name());
        private ObjectProperty<ImageView> image = new SimpleObjectProperty<>(this, IndexFieldName.image.name());

        public TextArea getParentUrl() {
            return parentUrl.get();
        }

        public ObjectProperty<TextArea> parentUrlProperty() {
            return parentUrl;
        }

        public void setParentUrl(TextArea parentUrl) {
            this.parentUrl.set(parentUrl);
        }

        public TextArea getUrl() {
            return url.get();
        }

        public ObjectProperty<TextArea> urlProperty() {
            return url;
        }

        public void setUrl(TextArea url) {
            this.url.set(url);
        }

        public String getType() {
            return type.get();
        }

        public StringProperty typeProperty() {
            return type;
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public String getExt() {
            return ext.get();
        }

        public StringProperty extProperty() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext.set(ext);
        }

        public TextArea getContent() {
            return content.get();
        }

        public ObjectProperty<TextArea> contentProperty() {
            return content;
        }

        public void setContent(TextArea content) {
            this.content.set(content);
        }

        public ImageView getImage() {
            return image.get();
        }

        public ObjectProperty<ImageView> imageProperty() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image.set(image);
        }

        public void clear() {
            setParentUrl(null);
            setUrl(null);
            setType(null);
            setExt(null);
            setContent(null);
            setImage(null);
        }
    }
}
