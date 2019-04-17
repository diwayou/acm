package com.diwayou.web.http.driver;

import com.diwayou.web.http.robot.RobotDriver;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.html.HTMLDocument;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class FxRobotDriver implements RobotDriver {

    private static final Logger log = Logger.getLogger(FxRobotDriver.class.getName());

    private JFXPanel p = new JFXPanel();

    private AtomicReference<WebEngine> engine = new AtomicReference<>();

    public FxRobotDriver() {
        // 启用CORS
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        AppThread.exec(() -> {
            WebView v = new WebView();
            Scene s = new Scene(v);
            p.setScene(s);

            engine.set(v.getEngine());
            return null;
        });
    }

    @Override
    public void close() {
        Platform.exit();
    }

    @Override
    public Object executeScript(String script) {
        return AppThread.exec(() -> engine.get().executeScript(script));
    }

    @Override
    public void get(String url) {
        AppThread.exec(() -> {engine.get().load(url); return null;});
    }

    @Override
    public HTMLDocument getDocument() {
        return AppThread.exec(() -> (HTMLDocument)engine.get().getDocument());
    }
}