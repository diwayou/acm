package com.diwayou.acm.http;

import com.diwayou.acm.common.Pool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class HttpRobotPool extends Pool<HttpRobot> {

    public HttpRobotPool() {
        this(new GenericObjectPoolConfig());
    }

    public HttpRobotPool(GenericObjectPoolConfig poolConfig) {

        super(poolConfig, new HttpRobotFactory());
    }

}
