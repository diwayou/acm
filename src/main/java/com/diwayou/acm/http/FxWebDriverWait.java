package com.diwayou.acm.http;

import javafx.scene.web.WebEngine;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class FxWebDriverWait extends FluentWait<AtomicReference<WebEngine>> {
    private final AtomicReference<WebEngine> driver;

    public FxWebDriverWait(AtomicReference<WebEngine> driver,  long timeOutInSeconds) {
        this(driver,
                java.time.Clock.systemDefaultZone(),
                Sleeper.SYSTEM_SLEEPER,
                timeOutInSeconds,
                DEFAULT_SLEEP_TIMEOUT);
    }

    public FxWebDriverWait(
            AtomicReference<WebEngine> driver,
            java.time.Clock clock,
            Sleeper sleeper,
            long timeOutInSeconds,
            long sleepTimeOut) {
        super(driver, clock, sleeper);
        withTimeout(Duration.ofSeconds(timeOutInSeconds));
        pollingEvery(Duration.ofMillis(sleepTimeOut));
        ignoring(NotFoundException.class);
        this.driver = driver;
    }
}
