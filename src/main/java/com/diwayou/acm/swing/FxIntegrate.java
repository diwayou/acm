package com.diwayou.acm.swing;

import com.diwayou.acm.http.DocumentUtil;
import com.diwayou.acm.http.FxWebDriverWait;
import com.diwayou.acm.http.TgouFxPageLoadReady;
import com.sun.webkit.dom.HTMLDocumentImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class FxIntegrate {
    public static void main(String[] args) {
        // 启用CORS
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        final JFXPanel p = new JFXPanel();
        AtomicReference<WebEngine> engineHolder = new AtomicReference<>();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WebView v = new WebView();
                Scene s = new Scene(v);
                p.setScene(s);

                WebEngine engine = v.getEngine();
                engineHolder.set(engine);

                System.out.println(engine.getUserAgent());
                engine.setOnError(new EventHandler<WebErrorEvent>() {
                    @Override
                    public void handle(WebErrorEvent event) {
                        System.out.println(event.toString());
                    }
                });
                engine.setUserDataDirectory(new File("."));
                engine.getLoadWorker().stateProperty().addListener(
                        new ChangeListener<Worker.State>() {
                            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                                System.out.println(String.format("Work Done old=%s, new=%s", oldState, newState));

                                if (newState == Worker.State.SUCCEEDED) {
                                    System.out.println(engine.getLocation());
                                }
                            }
                        });

                engine.load("https://m.51tiangou.com");

            }
        });

        FxWebDriverWait wait = new FxWebDriverWait(engineHolder, 10);
        wait.until(new TgouFxPageLoadReady());

        Platform.runLater(() -> {
            HTMLDocumentImpl document = (HTMLDocumentImpl) engineHolder.get().getDocument();
            try {
                System.out.println(DocumentUtil.toString(document));
            } catch (Exception e) {
                e.printStackTrace();
            }

            NodeList nodeList = document.getElementsByTagName("img");

            for (int i = 0; i < nodeList.getLength(); i++) {
                System.out.println(nodeList.item(i).getAttributes().getNamedItem("src").getTextContent());
            }

        });

        JFrame f = new JFrame();
        f.getContentPane().add(p);
        f.pack();
        f.setVisible(true);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
