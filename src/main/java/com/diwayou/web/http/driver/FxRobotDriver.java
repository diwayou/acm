package com.diwayou.web.http.driver;

import com.diwayou.web.config.RobotConfig;
import com.diwayou.web.http.robot.RobotDriver;
import com.diwayou.web.log.AppLog;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.LoadListenerClient;
import com.sun.webkit.WebPage;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.html.HTMLDocument;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FxRobotDriver implements RobotDriver {

    private static final Logger log = AppLog.getRobot();

    private JFXPanel panel = new JFXPanel();

    private AtomicReference<WebEngine> engine = new AtomicReference<>();

    private AtomicReference<WebPage> page = new AtomicReference<>();

    private AtomicReference<WebView> view = new AtomicReference<>();

    static {
        // 启用CORS
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }

    public FxRobotDriver() {
        ReflectiveOperationException re = AppThread.exec(() -> {
            WebView v = new WebView();
            view.set(v);
            //v.setContextMenuEnabled(false);

            Scene s = new Scene(v);
            panel.setScene(s);

            WebEngine webEngine = v.getEngine();
            webEngine.setUserAgent(RobotConfig.getDefaultAgent());
            engine.set(webEngine);

            WebPage webPage = Accessor.getPageFor(webEngine);
            page.set(webPage);

            return null;
        });

        if (re != null) {
            throw new RuntimeException(re);
        }
    }

    public JFXPanel getPanel() {
        return panel;
    }

    public String getUrl() {
        return AppThread.exec(() -> engine.get().getLocation());
    }

    @Override
    public void close() {
        Platform.exit();
    }

    @Override
    public Object executeScript(String script) {
        return AppThread.exec(() -> {
            try {
                return engine.get().executeScript(script);
            } catch (Throwable t) {
                log.log(Level.WARNING, "", t);
                return null;
            }
        });
    }

    @Override
    public void get(String url) {
        AppThread.exec(() -> {
            engine.get().load(url);
            return null;
        });
    }

    @Override
    public HTMLDocument getDocument() {
        return AppThread.exec(() -> (HTMLDocument) engine.get().getDocument());
    }

    @Override
    public void addResourceLoadListener(LoadListenerClient listener) {
        AppThread.exec(() -> {
            page.get().addLoadListenerClient(listener);
            return null;
        });
    }

    public WebView getView() {
        return view.get();
    }
}
