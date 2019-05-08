package com.diwayou.web.app.example;

import com.diwayou.web.concurrent.FixedBlockingExecutor;
import com.diwayou.web.config.CrawlConfig;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.crawl.handler.ScriptCrawlPageHandler;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.script.ScriptRegistry;

import java.io.File;

public class Dengziqi {
    public static void main(String[] args) throws Exception {
        File dir = new File(ClassLoader.getSystemResource("scripts").toURI());
        ScriptRegistry.one().load(dir);

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

        Request request = new Request("http://geteverybodymoving.com/")
                .setFetcherType(FetcherType.FX_WEBVIEW)
                .setTimeout(5);
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
