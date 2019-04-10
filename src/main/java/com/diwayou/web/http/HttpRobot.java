package com.diwayou.web.http;

import com.diwayou.web.common.Pool;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.Closeable;

public class HttpRobot implements Closeable {

    public static final int DEFAULT_TIMEOUT = 3;

    private ChromeOptions chromeOptions;

    private WebDriver webDriver;

    private Pool<HttpRobot> pool;

    public HttpRobot() {
        this(null);
    }

    public HttpRobot(Pool<HttpRobot> pool) {
        this.pool = pool;
        chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        webDriver = new ChromeDriver(ChromeDriverServiceSingleton.getInstance().getChromeDriverService(), chromeOptions);
    }

    public String get(String url, long timeOutInSeconds, PageLoadReady<WebDriver> pageLoadReady) {
        Preconditions.checkArgument(StringUtils.isNotBlank(url), "url不能为空");
        Preconditions.checkNotNull(pageLoadReady, "pageLoadReady不能为空");

        webDriver.get(url);

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, timeOutInSeconds);
        webDriverWait.until(pageLoadReady);

        WebElement webElement = webDriver.findElement(By.xpath("/html"));

        return webElement.getAttribute("outerHTML");
    }

    public String get(String url, long timeOutInSeconds) {
        return get(url, timeOutInSeconds, new DefaultPageLoadReady<>(timeOutInSeconds));
    }

    public String get(String url) {
        return get(url, DEFAULT_TIMEOUT);
    }

    @Override
    public void close() {
        if (pool != null) {
            pool.returnResource(this);
        } else {
            quit();
        }
    }

    public void quit() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
