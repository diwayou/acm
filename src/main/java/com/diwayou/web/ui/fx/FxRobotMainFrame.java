package com.diwayou.web.ui.fx;

import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.http.robot.HttpRobot;
import javafx.scene.layout.GridPane;

public class FxRobotMainFrame extends GridPane {

    private HttpRobot robot;

    public FxRobotMainFrame() {
        add(new FxToolPanel(this), 0, 0);

        robot = FetcherFactory.one().getFxWebviewFetcher().getRobot();

        add(robot.getView(), 0, 1);
    }

    public HttpRobot getRobot() {
        return robot;
    }
}
