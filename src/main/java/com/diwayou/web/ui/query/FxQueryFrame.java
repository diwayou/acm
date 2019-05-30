package com.diwayou.web.ui.query;

import com.diwayou.web.log.AppLog;
import com.diwayou.web.store.IndexFieldName;
import com.diwayou.web.store.QueryResult;
import com.google.common.collect.Lists;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FxQueryFrame extends Stage {

    private static final Logger log = AppLog.getBrowser();

    private TableView<Doc> tableView;

    private ObservableList<Doc> tableDocs;

    private ResultSearcher searcher;

    private int pageSize;

    private List<TableColumn<Doc, String>> columns;

    public FxQueryFrame(int pageSize) {
        this.pageSize = pageSize;

        List<Doc> docs = Lists.newArrayListWithCapacity(pageSize);
        for (int i = 0; i < pageSize; i++) {
            docs.add(new Doc());
        }

        tableDocs = FXCollections.observableArrayList(docs);
        tableView = new TableView<>(tableDocs);
        tableView.setFixedCellSize(300);

        columns = Lists.newArrayListWithCapacity(IndexFieldName.values().length);
        for (IndexFieldName fieldName : IndexFieldName.values()) {
            TableColumn<Doc, String> col = new TableColumn<>(fieldName.getDisplayName());
            col.setCellValueFactory(new PropertyValueFactory<>(fieldName.name()));

            if (fieldName.equals(IndexFieldName.parentUrl) || fieldName.equals(IndexFieldName.url)) {
                col.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
                col.setEditable(true);
            } else if (fieldName.equals(IndexFieldName.content)) {
                col.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
            } else if (fieldName.equals(IndexFieldName.image)) {
                col.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
            }

            columns.add(col);
        }
        tableView.getColumns().setAll(columns);

        GridPane grid = new GridPane();

        TextField textField = new TextField();
        textField.setOnAction(ae -> {
            String keyword = textField.getText();
            keyword = keyword == null ? "" : keyword;

            String finalKeyword = keyword;
            ForkJoinPool.commonPool().execute(() -> {
                try {
                    searcher.search(finalKeyword);
                } catch (Exception e) {
                    log.log(Level.WARNING, "", e);
                }
            });
        });
        grid.add(textField, 0, 0);

        grid.add(tableView, 0, 1);

        grid.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            tableView.setPrefSize(newValue.getWidth(), newValue.getHeight());
            tableView.autosize();
        });

        setScene(new Scene(grid, 1024, 768));

        try {
            this.searcher = new ResultSearcher(pageSize, (q, result) -> {
                display(result);
            });
        } catch (IOException e) {
            log.log(Level.WARNING, "", e);
        }
    }

    private void display(QueryResult result) {
        for (int row = 0; row < tableDocs.size(); row++) {
            if (row >= result.getDocs().size()) {
                tableDocs.set(row, new Doc());
                continue;
            }

            Document doc = result.getDocs().get(row);
            setRow(doc, tableDocs.get(row));
        }
    }

    private void setRow(Document doc, Doc row) {
        Text parentUrl = new Text(doc.get(IndexFieldName.parentUrl.name()));
        parentUrl.wrappingWidthProperty().bind(columns.get(0).widthProperty());
        row.setParentUrl(parentUrl);

        Text url = new Text(doc.get(IndexFieldName.url.name()));
        url.wrappingWidthProperty().bind(columns.get(1).widthProperty());
        row.setUrl(url);

        row.setType(doc.get(IndexFieldName.type.name()));
        row.setExt(doc.get(IndexFieldName.ext.name()));
        row.setContent(new TextArea(doc.get(IndexFieldName.content.name())));
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
        private ObjectProperty<Text> parentUrl = new SimpleObjectProperty<>(this, IndexFieldName.parentUrl.name());
        private ObjectProperty<Text> url = new SimpleObjectProperty<>(this, IndexFieldName.url.name());
        private StringProperty type = new SimpleStringProperty(this, IndexFieldName.type.name());
        private StringProperty ext = new SimpleStringProperty(this, IndexFieldName.ext.name());
        private ObjectProperty<TextArea> content = new SimpleObjectProperty<>(this, IndexFieldName.content.name());
        private StringProperty image = new SimpleStringProperty(this, IndexFieldName.image.name());

        public Text getParentUrl() {
            return parentUrl.get();
        }

        public ObjectProperty<Text> parentUrlProperty() {
            return parentUrl;
        }

        public void setParentUrl(Text parentUrl) {
            this.parentUrl.set(parentUrl);
        }

        public Text getUrl() {
            return url.get();
        }

        public ObjectProperty<Text> urlProperty() {
            return url;
        }

        public void setUrl(Text url) {
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

        public String getImage() {
            return image.get();
        }

        public StringProperty imageProperty() {
            return image;
        }

        public void setImage(String image) {
            this.image.set(image);
        }
    }
}
