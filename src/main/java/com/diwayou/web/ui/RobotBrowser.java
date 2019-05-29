package com.diwayou.web.ui;

import com.diwayou.web.config.AppConfig;
import com.diwayou.web.http.driver.AppThread;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.ui.fx.FxRobotMainFrame;
import com.diwayou.web.ui.swing.RobotMainFrame;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * C:\Java\jdk-12\bin\java -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y -jar acm-0.0.1-SNAPSHOT-jar-with-dependencies.jar
 */
public class RobotBrowser {

    public static void main(String[] args) throws IOException {
        AppLog.init(Paths.get("log"));

        Platform.startup(() -> AppLog.getBrowser().info("javafx初始化完成..."));

        AppThread.async(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);

            if (AppConfig.isFxRender()) {
                Scene scene = new Scene(new FxRobotMainFrame());

                Stage stage = new Stage();
                stage.setTitle("RobotBrowser");
                stage.setScene(scene);
                stage.sizeToScene();
                stage.show();
                stage.setOnCloseRequest(we -> {
                    Platform.exit();
                    System.exit(0);
                });
            } else {
                JFrame mainFrame;
                mainFrame = new RobotMainFrame();
                mainFrame.setSize(1024, 768);
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
