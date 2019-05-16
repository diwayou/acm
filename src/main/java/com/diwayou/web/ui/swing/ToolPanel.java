package com.diwayou.web.ui.swing;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Request;
import com.diwayou.web.ui.spider.SpiderSingleton;
import com.diwayou.web.url.UrlDict;
import org.pushingpixels.substance.api.SubstanceCortex;
import org.w3c.dom.html.HTMLDocument;

import javax.swing.*;
import java.awt.*;

public class ToolPanel extends JPanel {
    private static final String COMMIT_ACTION = "commit";

    public ToolPanel(RobotMainFrame mainFrame) {
        super(new FlowLayout(FlowLayout.LEFT));

        addUrlInput(mainFrame);

        addSpider(mainFrame);
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

    private void addUrlInput(RobotMainFrame mainFrame) {
        JTextField urlInputField = new JTextField("input a url", 50);
        urlInputField.setFocusTraversalKeysEnabled(false);
        SubstanceCortex.ComponentOrParentChainScope.setSelectTextOnFocus(urlInputField, true);

        Autocomplete autoComplete = new Autocomplete(urlInputField, UrlDict.WEBSITE_PROPOSALS);
        urlInputField.getDocument().addDocumentListener(autoComplete);

        urlInputField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
        urlInputField.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());

        urlInputField.addActionListener(ae -> {
            String url = ae.getActionCommand();

            if (!url.startsWith("http")) {
                url = "http://" + url;
            }

            try {
                mainFrame.getRobot().clear();

                mainFrame.getRobot().get(url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        this.add(urlInputField);
    }
}
