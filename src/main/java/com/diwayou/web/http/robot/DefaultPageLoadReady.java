package com.diwayou.web.http.robot;

import java.util.concurrent.TimeUnit;

public class DefaultPageLoadReady<T> implements PageLoadReady<T> {

    private HttpRobot httpRobot;

    private long timeOutInSeconds;

    private long createTime = System.currentTimeMillis();

    public DefaultPageLoadReady(HttpRobot httpRobot, long timeOutInSeconds) {
        this.httpRobot = httpRobot;
        this.timeOutInSeconds = timeOutInSeconds;
    }

    @Override
    public Boolean apply(T webDriver) {
        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - createTime) >= timeOutInSeconds) {
            return Boolean.TRUE;
        }

        if (httpRobot.getIsFinished().get()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
