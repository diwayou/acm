package com.diwayou.fx;

import com.diwayou.web.http.driver.AppThread;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StageTest extends Stage {
    public static void main(String[] args) {
        Platform.startup(() -> System.out.println("开始启动"));
        AppThread.async(() -> new StageTest().initAndShow());
    }

    public void initAndShow() {
        Text text = new Text(10, 40, "Hello World!");
        text.setFont(new Font(40));
        Scene scene = new Scene(new Group(text));

        Stage stage = new Stage();
        stage.setTitle("Welcome to JavaFX!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
