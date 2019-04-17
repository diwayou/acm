package com.diwayou.web.http.robot;

import com.diwayou.web.http.driver.FxRobotDriver;
import com.diwayou.web.support.DocumentUtil;
import com.diwayou.web.support.Pool;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.html.HTMLDocument;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpRobot implements Closeable {

    private static final Logger log = Logger.getLogger(HttpRobot.class.getName());

    public static final int DEFAULT_TIMEOUT = 3;

    private RobotDriver driver;

    private Pool<HttpRobot> pool;

    public HttpRobot() {
        this(null);
    }

    public HttpRobot(Pool<HttpRobot> pool) {
        this.pool = pool;
        driver = new FxRobotDriver();
    }

    public String get(String url, long timeOutInSeconds, PageLoadReady<RobotDriver> pageLoadReady) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(url), "url不能为空");
        Preconditions.checkNotNull(pageLoadReady, "pageLoadReady不能为空");

        driver.get(url);

        RobotDriverWait driverWait = new RobotDriverWait(driver, timeOutInSeconds);
        driverWait.until(pageLoadReady);

        HTMLDocument document = driver.getDocument();

        return DocumentUtil.toString(document);
    }

    public String get(String url, long timeOutInSeconds) throws Exception {
        return get(url, timeOutInSeconds, new DefaultPageLoadReady<>(timeOutInSeconds));
    }

    public String get(String url) throws Exception {
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
        try {
            driver.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "", e);
        }
    }
}
