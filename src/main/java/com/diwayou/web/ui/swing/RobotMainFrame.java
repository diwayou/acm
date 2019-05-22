package com.diwayou.web.ui.swing;

import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.http.robot.HttpRobot;

import javax.swing.*;
import java.awt.*;

public class RobotMainFrame extends JFrame {

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
}
