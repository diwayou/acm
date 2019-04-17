package com.diwayou.web.http;

import com.diwayou.web.http.robot.PageLoadReady;
import com.diwayou.web.http.robot.RobotDriver;

public class TgouPageLoadReady implements PageLoadReady<RobotDriver> {

    @Override
    public Boolean apply(RobotDriver driver) {
        String ready = (String) driver.executeScript("document.readyState");

        Boolean imageLoaded = (Boolean) driver.executeScript("document.getElementsByTagName('a').length > 2;");

        return "complete".equalsIgnoreCase(ready) && imageLoaded;
    }
}
