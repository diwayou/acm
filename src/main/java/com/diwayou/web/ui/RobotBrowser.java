package com.diwayou.web.ui;

import com.diwayou.web.ui.swing.RobotMainFrame;
import org.pushingpixels.substance.api.SubstanceCortex;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RobotBrowser {

    public static void main(String[] args) throws IOException {
        initLog();
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

    private static void initLog() throws IOException {
        FileHandler handler = new FileHandler("./browser.log");
        handler.setFormatter(new SimpleFormatter());

        Logger.getGlobal().addHandler(handler);
    }
}
