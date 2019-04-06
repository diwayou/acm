package com.diwayou.acm.swing;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;

public class FxIntegrate {
    public static void main(String[] args) {
        final JFXPanel p = new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WebView v = new WebView();
                Scene s = new Scene(v);
                p.setScene(s);

                WebEngine engine = v.getEngine();
                engine.load("https://www.qq.com");
                Platform.runLater(() -> System.out.println(engine.getDocument().toString()));

            }
        });

        JFrame f = new JFrame();
        f.getContentPane().add(p);
        f.pack();
        f.setVisible(true);
        f.setSize(1024, 768);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
