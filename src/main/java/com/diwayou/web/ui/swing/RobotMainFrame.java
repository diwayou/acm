package com.diwayou.web.ui.swing;

import com.diwayou.web.http.driver.FxRobotDriver;

import javax.swing.*;
import java.awt.*;

public class RobotMainFrame extends JFrame {

    private FxRobotDriver driver;

    private ToolPanel toolPanel;

    public RobotMainFrame() throws HeadlessException {
        super("RobotBrowser");
        this.setLayout(new BorderLayout());

        driver = new FxRobotDriver();
        this.add(driver.getPanel(), BorderLayout.CENTER);

        toolPanel = new ToolPanel(this);
        this.add(toolPanel, BorderLayout.NORTH);

        this.setJMenuBar(buildMenu());

        this.setResizable(true);
    }

    public FxRobotDriver getDriver() {
        return driver;
    }

    private JMenuBar buildMenu() {
        JMenuBar jmb = new JMenuBar();

        jmb.add(buildFileMenu());

        return jmb;
    }

    private JMenu buildFileMenu() {
        JMenu jmFile = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New");
        jmFile.add(newItem);

        return jmFile;
    }
}
