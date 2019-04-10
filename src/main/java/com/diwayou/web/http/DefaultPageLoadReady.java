package com.diwayou.web.http;

import java.util.concurrent.TimeUnit;

public class DefaultPageLoadReady<T> implements PageLoadReady<T> {
    private long timeOutInSeconds;

    public DefaultPageLoadReady(long timeOutInSeconds) {
        this.timeOutInSeconds = timeOutInSeconds;
    }

    @Override
    public Boolean apply(T webDriver) {
        try {
            TimeUnit.SECONDS.sleep(timeOutInSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Boolean.TRUE;
    }
}
