package com.diwayou.acm.http;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class DefaultPageLoadReady implements PageLoadReady {
    private long timeOutInSeconds;

    public DefaultPageLoadReady(long timeOutInSeconds) {
        this.timeOutInSeconds = timeOutInSeconds;
    }

    @Override
    public Boolean apply(WebDriver webDriver) {
        try {
            TimeUnit.SECONDS.sleep(timeOutInSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Boolean.TRUE;
    }
}
