package com.diwayou.web.fetcher;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.domain.StringPage;
import com.diwayou.web.http.robot.HttpRobot;
import com.diwayou.web.http.robot.HttpRobotPool;

public class FxWebviewFetcher implements Fetcher {

    private HttpRobotPool pool;

    public FxWebviewFetcher() {
        this.pool = new HttpRobotPool();
    }

    @Override
    public Page fetch(Request request) {
        try (HttpRobot robot = pool.getResource()) {
            String body = robot.get(request.getUrl(), request.getTimeout());

            return new StringPage(request, body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
