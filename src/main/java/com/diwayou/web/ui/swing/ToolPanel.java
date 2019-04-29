package com.diwayou.web.ui.swing;

import com.diwayou.web.url.UrlDict;
import org.pushingpixels.substance.api.SubstanceCortex;

import javax.swing.*;
import java.awt.*;

public class ToolPanel extends JPanel {
    private static final String COMMIT_ACTION = "commit";

    public ToolPanel(RobotMainFrame mainFrame) {
        super(new FlowLayout(FlowLayout.LEFT));

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

            mainFrame.getDriver().get(url);
        });

        this.add(urlInputField);
    }
}
