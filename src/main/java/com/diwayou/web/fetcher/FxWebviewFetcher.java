package com.diwayou.web.fetcher;

import com.diwayou.web.domain.*;
import com.diwayou.web.http.robot.HttpRobot;
import com.diwayou.web.http.robot.HttpRobotPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.w3c.dom.html.HTMLDocument;

public class FxWebviewFetcher implements Fetcher {

    private HttpRobotPool pool;

    public FxWebviewFetcher() {
        this.pool = new HttpRobotPool();
    }

    public FxWebviewFetcher(int poolCapacity) {
        GenericObjectPoolConfig<HttpRobot> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(poolCapacity);
        config.setMaxWaitMillis(10000);

        this.pool = new HttpRobotPool(config);
    }

    @Override
    public Page fetch(Request request) {
        try (HttpRobot robot = pool.getResource()) {
            HTMLDocument body = robot.get(request.getUrl(), request.getTimeout());

            return new HtmlDocumentPage(request, body);
        } catch (Exception e) {
            return new EmptyPage(request, e);
        }
    }

    @Override
    public FetcherType getType() {
        return FetcherType.FX_WEBVIEW;
    }

    @Override
    public void close() {
        this.pool.close();
    }
}
