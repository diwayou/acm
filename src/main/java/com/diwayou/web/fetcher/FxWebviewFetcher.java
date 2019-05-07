package com.diwayou.web.fetcher;

import com.diwayou.web.domain.*;
import com.diwayou.web.http.robot.DocumentInfo;
import com.diwayou.web.http.robot.HttpRobot;
import com.diwayou.web.http.robot.HttpRobotPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.List;

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
            DocumentInfo body = robot.get(request.getUrl(), request.getTimeout());

            if (request.getRequestScripts() != null && request.getRequestScripts() != null) {
                exeScript(robot, request.getRequestScripts());
            }

            return new HtmlDocumentPage(request, body.getHtmlDocument(), body.getResourceUrls());
        } catch (Exception e) {
            return new EmptyPage(request, e);
        }
    }

    private void exeScript(HttpRobot robot, List<RequestScript> requestScripts) {
        for (RequestScript requestScript : requestScripts) {
            robot.executeScript(requestScript.getSrc(), requestScript.getTimeout());
        }
    }

    /**
     * 使用该接口返回的robot需要自行关闭
     */
    public HttpRobot getRobot() {
        return pool.getResource();
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
