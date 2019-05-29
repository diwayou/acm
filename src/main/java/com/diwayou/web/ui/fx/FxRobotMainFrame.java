package com.diwayou.web.ui.fx;

import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.http.robot.HttpRobot;
import javafx.scene.layout.GridPane;

public class FxRobotMainFrame extends GridPane {

    private HttpRobot robot;

    private FxToolPanel toolPanel;

    public FxRobotMainFrame() {
        toolPanel = new FxToolPanel(this);
        add(toolPanel, 0, 0);

        robot = FetcherFactory.one().getFxWebviewFetcher().getRobot();

        add(robot.getView(), 0, 1);

        layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            toolPanel.setPrefWidth(newValue.getWidth());
            toolPanel.autosize();
            robot.getView().setPrefWidth(newValue.getWidth());
            robot.getView().autosize();
            autosize();
        });
    }

    public HttpRobot getRobot() {
        return robot;
    }
}
