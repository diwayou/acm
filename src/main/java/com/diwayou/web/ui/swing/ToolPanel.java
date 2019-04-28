package com.diwayou.web.ui.swing;

import org.pushingpixels.substance.api.SubstanceCortex;

import javax.swing.*;
import java.awt.*;

public class ToolPanel extends JPanel {
    public ToolPanel(RobotMainFrame mainFrame) {
        super(new FlowLayout(FlowLayout.LEFT));

        JTextField urlInputField = new JTextField("input a url", 50);
        SubstanceCortex.ComponentOrParentChainScope.setSelectTextOnFocus(urlInputField, true);
        urlInputField.addActionListener(ae -> {
            String url = ae.getActionCommand();

            if (!url.startsWith("http://")) {
                url = "http://" + url;
            }

            mainFrame.getDriver().get(url);
        });

        this.add(urlInputField);
    }
}
