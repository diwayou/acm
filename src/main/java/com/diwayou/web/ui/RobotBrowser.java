package com.diwayou.web.ui;

import com.diwayou.web.log.AppLog;
import com.diwayou.web.ui.swing.RobotMainFrame;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;

public class RobotBrowser {

    public static void main(String[] args) throws IOException {
        AppLog.init(Paths.get("log"));
        SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);

            RobotMainFrame mainFrame = new RobotMainFrame();
            mainFrame.setSize(1024, 768);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
