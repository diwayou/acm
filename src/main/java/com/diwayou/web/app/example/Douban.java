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
import com.diwayou.web.script.CrawlScript;
import com.diwayou.web.script.ScriptRegistry;
import com.google.common.collect.Maps;

import java.io.File;
import java.util.TreeMap;

public class Douban {
    public static void main(String[] args) throws Exception {
        TreeMap<String, CrawlScript> scripts = Maps.newTreeMap();
        File scriptFile = new File(ClassLoader.getSystemResource("scripts/All.groovy").toURI());
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

        Request request = new Request("https://www.douban.com")
                .setFetcherType(FetcherType.FX_WEBVIEW)
                .setTimeout(3);
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
