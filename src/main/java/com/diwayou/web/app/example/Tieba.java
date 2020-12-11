package com.diwayou.web.app.example;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.http.robot.HttpRobot;

import java.nio.file.Path;

public class Tieba {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("tieba"))
                .setCrawlThreadNum(1)
                .build();

        Request request = new Request("https://tieba.baidu.com")
                .setFetcherType(FetcherType.FxWebView);
        try (HttpRobot robot = FetcherFactory.one().getFxWebviewFetcher().getRobot()) {
            robot.get("http://tieba.baidu.com/f?ie=utf-8&kw=%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C&fr=search&red_tag=p2630938194", 10);

            // 抓取前10页结果
            for (int i = 1; i < 10; i++) {
                spider.submitRequest(request.setUrl(robot.getDocument().getURL()));

                // 清除url记录
                robot.clear();

                robot.executeScript("document.getElementsByClassName('search_btn_enter_ba')[0].click()", 5);
            }
        }

        spider.waitUntilStop();
    }
}
