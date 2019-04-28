package com.diwayou.web.ui;

import com.diwayou.web.ui.swing.RobotMainFrame;
import org.pushingpixels.substance.api.SubstanceCortex;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

import javax.swing.*;

public class RobotBrowser {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SubstanceCortex.GlobalScope.setSkin(new GraphiteSkin());
            JFrame.setDefaultLookAndFeelDecorated(true);

            RobotMainFrame mainFrame = new RobotMainFrame();
            mainFrame.setSize(800, 600);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
