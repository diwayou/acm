package com.diwayou.web.app.example;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.http.robot.HttpRobot;

import java.nio.file.Path;

public class Baidu {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("D:/tmp"))
                .setCrawlThreadNum(1)
                .setScriptsPath(Path.of(ClassLoader.getSystemResource("scripts").toURI()))
                .build();

        Request request = new Request("https://www.baidu.com")
                .setFetcherType(FetcherType.FX_WEBVIEW);
        try (HttpRobot robot = FetcherFactory.one().getFxWebviewFetcher().getRobot()) {
            robot.get("https://www.baidu.com", 2);
            robot.executeScript("document.getElementById('kw').value = '计算机网络'", 1);
            robot.executeScript("document.getElementById('su').click()", 3);

            // 抓取前10页结果
            for (int i = 1; i < 10; i++) {
                spider.submitPage(new HtmlDocumentPage(request, robot.getDocument(), robot.getRequestUrls()));

                // 清除url记录
                robot.clear();

                robot.executeScript("a = document.getElementsByClassName('n'); a[a.length - 1].click()", 1);
            }
        }

        spider.waitUntilStop();
    }
}
