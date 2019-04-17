package com.diwayou.web.http.robot;

import com.diwayou.web.support.Sleeper;

import java.time.Duration;

public class DefaultPageLoadReady<T> implements PageLoadReady<T> {

    private long timeOutInSeconds;

    public DefaultPageLoadReady(long timeOutInSeconds) {
        this.timeOutInSeconds = timeOutInSeconds;
    }

    @Override
    public Boolean apply(T webDriver) {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(timeOutInSeconds));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Boolean.TRUE;
    }
}
