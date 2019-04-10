package com.diwayou.web.http;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class TgouPageLoadReady implements PageLoadReady<WebDriver> {
    @Override
    public Boolean apply(WebDriver webDriver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String ready = (String) jsExecutor.executeScript("return document.readyState");

        Boolean imageLoaded = (Boolean) jsExecutor.executeScript("return $('a').length > 2");

        return "complete".equalsIgnoreCase(ready) && imageLoaded;
    }
}
