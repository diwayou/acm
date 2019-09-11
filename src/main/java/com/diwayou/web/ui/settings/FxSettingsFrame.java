package com.diwayou.web.ui.settings;

import com.diwayou.web.config.AppConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;

import java.util.regex.Pattern;

public class FxSettingsFrame extends Dialog {

    private final FlowPane flow;

    public FxSettingsFrame() {
        setTitle("设置");

        flow = new FlowPane();
        flow.setVgap(8);
        flow.setHgap(4);
        flow.setPrefWrapLength(600);

        final DialogPane dialogPane = getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        updateGrid();
    }

    private void updateGrid() {
        flow.getChildren().clear();

        addSetting("图片", AppConfig.isStoreImage(), ie -> {
            CheckBox checkBox = (CheckBox) ie.getSource();
            if (checkBox.isSelected()) {
                AppConfig.toggleImage(true);
            } else {
                AppConfig.toggleImage(false);
            }
        });
        addSetting("文档", AppConfig.isStoreDoc(), ie -> {
            CheckBox checkBox = (CheckBox) ie.getSource();
            if (checkBox.isSelected()) {
                AppConfig.toggleDoc(true);
            } else {
                AppConfig.toggleDoc(false);
            }
        });
        addSetting("页面", AppConfig.isStoreHtml(), ie -> {
            CheckBox checkBox = (CheckBox) ie.getSource();
            if (checkBox.isSelected()) {
                AppConfig.toggleHtml(true);
            } else {
                AppConfig.toggleHtml(false);
            }
        });
        addSetting("系统浏览器浏览图片", AppConfig.isSystemBrowser(), ie -> {
            CheckBox checkBox = (CheckBox) ie.getSource();
            if (checkBox.isSelected()) {
                AppConfig.toggleSystemBrowser(true);
            } else {
                AppConfig.toggleSystemBrowser(false);
            }
        });
        addSetting("使用fx渲染界面(需重启)", AppConfig.isFxRender(), ie -> {
            CheckBox checkBox = (CheckBox) ie.getSource();
            if (checkBox.isSelected()) {
                AppConfig.toggleFxRender(true);
            } else {
                AppConfig.toggleFxRender(false);
            }
        });

        addImageLength();

        getDialogPane().setContent(flow);
    }

    private void addImageLength() {
        HBox box = new HBox(4);

        Label label = new Label("抓取图片大小限制(字节): ");
        HBox.setMargin(label, new Insets(5, 5, 5, 5));
        box.getChildren().add(label);

        TextField textField = new TextField();
        textField.setPrefColumnCount(15);
        textField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), AppConfig.getImageLength(),
                c -> Pattern.matches("\\d*", c.getText()) ? c : null));
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) {
                AppConfig.setImageLengthLimit(Integer.parseInt(textField.getText()));
            }
        });
        HBox.setMargin(textField, new Insets(5, 5, 5, 5));
        box.getChildren().add(textField);

        flow.getChildren().add(box);
    }

    private void addSetting(String label, boolean state, EventHandler<ActionEvent> handler) {
        CheckBox checkBox = new CheckBox(label);
        checkBox.setSelected(state);
        checkBox.setOnAction(handler);

        flow.getChildren().add(checkBox);
    }
}
