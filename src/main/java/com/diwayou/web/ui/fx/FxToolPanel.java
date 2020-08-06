package com.diwayou.web.ui.fx;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Request;
import com.diwayou.web.http.driver.AppThread;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.ui.event.GlobalEventBus;
import com.diwayou.web.ui.event.UpdateUrlEvent;
import com.diwayou.web.ui.query.FxQueryFrame;
import com.diwayou.web.ui.settings.FxSettingsFrame;
import com.diwayou.web.ui.spider.SpiderSingleton;
import com.diwayou.web.url.UrlDict;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.w3c.dom.html.HTMLDocument;

import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FxToolPanel extends HBox {
    private static final Logger log = AppLog.getBrowser();

    private TextField urlInputField;

    public FxToolPanel(FxRobotMainFrame mainFrame) {
        super();

        addUrlInput(mainFrame);

        addSpider(mainFrame);

        addSettings();

        addMore(mainFrame);

        GlobalEventBus.one().register(this);
    }

    private void addMore(FxRobotMainFrame mainFrame) {
        MenuButton button = new MenuButton("更多");
        setMargin(button, new Insets(5, 5, 5, 5));

        MenuItem scriptItem = new MenuItem("脚本");


        MenuItem searchItem = new MenuItem("查询");
        searchItem.setOnAction(ae -> {
            FxQueryFrame stage = new FxQueryFrame(5);
            stage.setTitle("查询");
            stage.sizeToScene();
            stage.setOnCloseRequest(we -> stage.close());
            stage.show();
        });

        MenuItem imageItem = new MenuItem("浏览图片");

        button.getItems().addAll(scriptItem, searchItem, imageItem);
        getChildren().add(button);
    }

    private void addSettings() {
        addButton("设置", ae -> new FxSettingsFrame().showAndWait());
    }

    private void addSpider(FxRobotMainFrame mainFrame) {
        addButton("爬取", e -> {
            HTMLDocument doc = mainFrame.getRobot().getDocument();
            if (doc == null) {
                return;
            }

            Request request = new Request(doc.getURL())
                    .setFetcherType(FetcherType.FX_WEBVIEW)
                    .setPriority(Request.MAX_PRIORITY);

            SpiderSingleton.one().submitPage(new HtmlDocumentPage(request, doc, mainFrame.getRobot().getRequestUrls()));
        });
    }

    private void addButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        setMargin(button, new Insets(5, 5, 5, 5));
        button.setOnAction(handler);

        getChildren().add(button);
    }

    private void addUrlInput(FxRobotMainFrame mainFrame) {
        urlInputField = new AutoCompletionTextField(Sets.newTreeSet(UrlDict.WEBSITE_PROPOSALS));

        setMargin(urlInputField, new Insets(5, 5, 5, 5));
        setHgrow(urlInputField, Priority.ALWAYS);
        urlInputField.setPrefColumnCount(50);

        urlInputField.setOnAction(ae -> {
            String url = urlInputField.getText();

            if (!url.startsWith("http")) {
                url = "http://" + url;
            }

            final String fUrl = url;
            mainFrame.getRobot().clear();

            ForkJoinPool.commonPool().execute(() -> {
                try {
                    mainFrame.getRobot().get(fUrl, 10);
                    updateUrl(new UpdateUrlEvent().setUrl(mainFrame.getRobot().getUrl()));
                } catch (Exception e) {
                    log.log(Level.WARNING, "", e);
                }
            });
        });

        getChildren().add(urlInputField);
    }

    @Subscribe
    public void updateUrl(UpdateUrlEvent event) {
        AppThread.async(() -> urlInputField.setText(event.getUrl()));
    }
}
