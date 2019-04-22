package com.diwayou.web.http.robot;

import com.diwayou.web.http.driver.FxRobotDriver;
import com.diwayou.web.support.Pool;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.sun.webkit.LoadListenerClient;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpRobot implements Closeable {

    private static final Logger log = Logger.getLogger(HttpRobot.class.getName());

    public static final int DEFAULT_TIMEOUT = 3;

    private RobotDriver driver;

    private Pool<HttpRobot> pool;

    // 记录每次请求加载的资源url
    private volatile Set<String> requestUrls;

    public HttpRobot() {
        this.driver = new FxRobotDriver();

        driver.addResourceLoadListener(new LoadListenerClient() {
            @Override
            public void dispatchLoadEvent(long frame, int state, String url, String contentType, double progress, int errorCode) {

            }

            @Override
            public void dispatchResourceLoadEvent(long frame, int state, String url, String contentType, double progress, int errorCode) {
                if (requestUrls == null) {
                    requestUrls = Collections.synchronizedSet(Sets.newHashSet());
                }

                requestUrls.add(url);
            }
        });
    }

    public DocumentInfo get(String url, long timeOutInSeconds, PageLoadReady<RobotDriver> pageLoadReady) throws Exception {
        Preconditions.checkArgument(StringUtils.isNotBlank(url), "url不能为空");
        Preconditions.checkNotNull(pageLoadReady, "pageLoadReady不能为空");

        driver.get(url);

        RobotDriverWait driverWait = new RobotDriverWait(driver, timeOutInSeconds);
        driverWait.until(pageLoadReady);

        Set<String> curRequestUrls = requestUrls;
        requestUrls = null;

        return new DocumentInfo(driver.getDocument(), curRequestUrls);
    }

    public DocumentInfo get(String url, long timeOutInSeconds) throws Exception {
        return get(url, timeOutInSeconds, new DefaultPageLoadReady<>(timeOutInSeconds));
    }

    public DocumentInfo get(String url) throws Exception {
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
