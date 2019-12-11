package com.diwayou.web.ui.swing;

import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.http.robot.HttpRobot;
import com.diwayou.web.log.AppLog;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotMainFrame extends JFrame {

    private static final Logger log = AppLog.getBrowser();

    private HttpRobot robot;

    private ToolPanel toolPanel;

    public RobotMainFrame() throws HeadlessException {
        super("RobotBrowser");
        this.setLayout(new BorderLayout());

        robot = FetcherFactory.one().getFxWebviewFetcher().getRobot();
        this.add(robot.getPanel(), BorderLayout.CENTER);

        toolPanel = new ToolPanel(this);
        this.add(toolPanel, BorderLayout.NORTH);

        this.setResizable(true);
    }

    public HttpRobot getRobot() {
        return robot;
    }

    public void navUrl(String url, int timeout) {
        SwingUtilities.invokeLater(() -> {
            try {
                robot.get(url, timeout);
            } catch (Exception ex) {
                log.log(Level.WARNING, "", ex);
                JOptionPane.showInternalMessageDialog(null, "加载失败e=" + ex.getMessage(), "警告", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
