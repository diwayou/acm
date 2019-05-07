package com.diwayou.web.app.example;

import com.diwayou.web.concurrent.FixedBlockingExecutor;
import com.diwayou.web.config.CrawlConfig;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.crawl.handler.ScriptCrawlPageHandler;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.http.robot.HttpRobot;
import com.diwayou.web.script.CrawlScript;
import com.diwayou.web.script.ScriptRegistry;
import com.google.common.collect.Maps;

import java.io.File;
import java.util.TreeMap;

public class Baidu {
    public static void main(String[] args) throws Exception {
        TreeMap<String, CrawlScript> scripts = Maps.newTreeMap();
        File scriptFile = new File(ClassLoader.getSystemResource("scripts/Download.groovy").toURI());
        scripts.put(ScriptRegistry.DOMAIN_ALL, new CrawlScript(scriptFile));
        ScriptRegistry.one().load(scripts);

        CrawlConfig crawlConfig = new CrawlConfig()
                .setMaxDepth(6);
        PageHandler handler = new ScriptCrawlPageHandler(crawlConfig);

        FetcherFactory fetcherFactory = new FetcherFactory();

        FixedBlockingExecutor requestExecutor = new FixedBlockingExecutor(2);
        FixedBlockingExecutor pageExecutor = new FixedBlockingExecutor(2);
        Spider spider = SpiderBuilder.newBuilder(fetcherFactory, handler)
                .setRequestExecutor(requestExecutor)
                .setPageExecutor(pageExecutor)
                .build();

        Request request = new Request("https://www.baidu.com")
                .setFetcherType(FetcherType.FX_WEBVIEW);
        try (HttpRobot robot = fetcherFactory.getFxWebviewFetcher().getRobot()) {
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
