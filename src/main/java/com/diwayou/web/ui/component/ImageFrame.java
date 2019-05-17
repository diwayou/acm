package com.diwayou.web.ui.component;

import com.diwayou.web.log.AppLog;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class ImageFrame extends JFrame {

    private static final Logger log = AppLog.getBrowser();

    public ImageFrame(JFrame parentFrame, String title, String path) {
        setLayout(new BorderLayout());
        JLabel photographLabel = new JLabel();

        photographLabel.setVerticalTextPosition(JLabel.BOTTOM);
        photographLabel.setHorizontalTextPosition(JLabel.CENTER);
        photographLabel.setHorizontalAlignment(JLabel.CENTER);
        photographLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(photographLabel, BorderLayout.CENTER);

        ImageIcon image = new ImageIcon(path);
        photographLabel.setIcon(image);

        setTitle(title);
        setSize(image.getIconWidth(), image.getIconHeight());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parentFrame);
    }
}
