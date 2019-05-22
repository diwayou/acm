package com.diwayou.web.ui.swing;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Request;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.ui.query.QueryFrame;
import com.diwayou.web.ui.script.ScriptFrame;
import com.diwayou.web.ui.settings.SettingsFrame;
import com.diwayou.web.ui.spider.SpiderSingleton;
import com.diwayou.web.url.UrlDict;
import org.w3c.dom.html.HTMLDocument;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToolPanel extends JPanel {
    private static final Logger log = AppLog.getBrowser();

    private static final String COMMIT_ACTION = "commit";

    private JTextField urlInputField;

    public ToolPanel(RobotMainFrame mainFrame) {
        super(new FlowLayout(FlowLayout.LEFT));

        addUrlInput(mainFrame);

        addSpider(mainFrame);

        addScript(mainFrame);

        addQuery(mainFrame);

        addSettings(mainFrame);
    }

    private void addSettings(RobotMainFrame mainFrame) {
        JButton settingsButton = new JButton("设置");
        settingsButton.addActionListener(ae -> {
            new SettingsFrame(mainFrame)
                    .setVisible(true);
        });

        this.add(settingsButton);
    }

    private void addQuery(RobotMainFrame mainFrame) {
        JButton scriptButton = new JButton("查询");
        scriptButton.addActionListener(ae -> {
            SwingUtilities.invokeLater(() -> new QueryFrame(mainFrame, this)
                    .setVisible(true));
        });

        this.add(scriptButton);
    }

    private void addSpider(RobotMainFrame mainFrame) {
        JButton spiderButton = new JButton("爬取");
        spiderButton.addActionListener(e -> {
            HTMLDocument doc = mainFrame.getRobot().getDocument();
            if (doc == null) {
                return;
            }


            Request request = new Request(doc.getURL())
                    .setFetcherType(FetcherType.FX_WEBVIEW)
                    .setPriority(Request.MAX_PRIORITY);

            SpiderSingleton.one().submitPage(new HtmlDocumentPage(request, doc, mainFrame.getRobot().getRequestUrls()));
        });

        this.add(spiderButton);
    }

    private void addScript(RobotMainFrame mainFrame) {
        JButton scriptButton = new JButton("脚本");
        scriptButton.addActionListener(ae -> {
            new ScriptFrame(mainFrame)
                    .setVisible(true);
        });

        this.add(scriptButton);
    }

    private void addUrlInput(RobotMainFrame mainFrame) {
        urlInputField = new JTextField("", 50);
        urlInputField.setFocusTraversalKeysEnabled(false);

        AutoComplete autoComplete = new AutoComplete(urlInputField, UrlDict.WEBSITE_PROPOSALS);
        urlInputField.getDocument().addDocumentListener(autoComplete);

        urlInputField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
        urlInputField.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());

        urlInputField.addActionListener(ae -> {
            String url = ae.getActionCommand();

            if (!url.startsWith("http")) {
                url = "http://" + url;
            }

            final String fUrl = url;
            mainFrame.getRobot().clear();

            ForkJoinPool.commonPool().execute(() -> {
                try {
                    mainFrame.getRobot().get(fUrl, 10);
                    updateUrl(mainFrame.getRobot().getUrl());
                } catch (Exception e) {
                    log.log(Level.WARNING, "", e);
                }
            });
        });

        this.add(urlInputField);
    }

    public void updateUrl(String url) {
        SwingUtilities.invokeLater(() -> urlInputField.setText(url));
    }
}
