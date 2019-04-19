package com.diwayou.web.http.robot;

import com.diwayou.web.http.driver.FxRobotDriver;
import com.diwayou.web.support.Pool;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.html.HTMLDocument;

import java.io.Closeable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpRobot implements Closeable {

    private static final Logger log = Logger.getLogger(HttpRobot.class.getName());

    public static final int DEFAULT_TIMEOUT = 3;

    private RobotDriver driver;

    private Pool<HttpRobot> pool;

    public HttpRobot() {
        this.driver = new FxRobotDriver();
    }

    public HTMLDocument get(String url, long timeOutInSeconds, PageLoadReady<RobotDriver> pageLoadReady) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(url), "url不能为空");
        Preconditions.checkNotNull(pageLoadReady, "pageLoadReady不能为空");

        driver.get(url);

        RobotDriverWait driverWait = new RobotDriverWait(driver, timeOutInSeconds);
        driverWait.until(pageLoadReady);

        return driver.getDocument();
    }

    public HTMLDocument get(String url, long timeOutInSeconds) throws Exception {
        return get(url, timeOutInSeconds, new DefaultPageLoadReady<>(timeOutInSeconds));
    }

    public HTMLDocument get(String url) throws Exception {
        return get(url, DEFAULT_TIMEOUT);
    }

    @Override
    public void close() {
        if (pool != null) {
            driver.get("about:blank");
            pool.returnResource(this);
        } else {
            quit();
        }
    }

    public void quit() {
        try {
            driver.close();
        } catch (Exception e) {
            log.log(Level.SEVERE, "", e);
        }
    }

    public HttpRobot setPool(Pool<HttpRobot> pool) {
        this.pool = pool;
        return this;
    }
}
