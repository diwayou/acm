package com.diwayou.web.http.robot;

import com.diwayou.web.support.Pool;
import javafx.application.Platform;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class HttpRobotPool extends Pool<HttpRobot> {

    public HttpRobotPool() {
        this(new GenericObjectPoolConfig());
    }

    public HttpRobotPool(GenericObjectPoolConfig poolConfig) {

        super(poolConfig, new HttpRobotFactory());
    }

    @Override
    public HttpRobot getResource() {
        HttpRobot robot = super.getResource();
        robot.setPool(this);

        return robot;
    }

    @Override
    public void close() {
        super.close();
        Platform.exit();
    }
}
