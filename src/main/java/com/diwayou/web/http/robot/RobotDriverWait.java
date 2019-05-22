package com.diwayou.web.http.robot;

import com.diwayou.web.support.FluentWait;
import com.diwayou.web.support.Sleeper;

import java.time.Duration;

public class RobotDriverWait extends FluentWait<HttpRobot> {
    private final HttpRobot driver;

    public RobotDriverWait(HttpRobot driver, long timeOutInSeconds) {
        this(driver,
                java.time.Clock.systemDefaultZone(),
                Sleeper.SYSTEM_SLEEPER,
                timeOutInSeconds,
                DEFAULT_SLEEP_TIMEOUT);
    }

    public RobotDriverWait(
            HttpRobot driver,
            java.time.Clock clock,
            Sleeper sleeper,
            long timeOutInSeconds,
            long sleepTimeOut) {
        super(driver, clock, sleeper);
        withTimeout(Duration.ofSeconds(timeOutInSeconds));
        pollingEvery(Duration.ofMillis(sleepTimeOut));
        //ignoring(NotFoundException.class);
        this.driver = driver;
    }
}
