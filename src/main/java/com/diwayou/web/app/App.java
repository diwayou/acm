package com.diwayou.web.app;

import com.diwayou.web.concurrent.FixedBlockingExecutor;
import com.diwayou.web.config.CrawlConfig;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.crawl.handler.ScriptCrawlPageHandler;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;
import com.diwayou.web.domain.RequestScript;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.script.CrawlScript;
import com.diwayou.web.script.ScriptRegistry;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;

public class App {
    public static void main(String[] args) throws Exception {
        TreeMap<String, CrawlScript> scripts = Maps.newTreeMap();
        Path path = Path.of(App.class.getClassLoader().getResource("scripts/DownloadImage.groovy").toURI());
        scripts.put(ScriptRegistry.DOMAIN_ALL, new CrawlScript(Files.readString(path)));
        ScriptRegistry.one().load(scripts);

        CrawlConfig crawlConfig = new CrawlConfig();
        PageHandler handler = new ScriptCrawlPageHandler(crawlConfig);

        FetcherFactory fetcherFactory = new FetcherFactory();

        FixedBlockingExecutor requestExecutor = new FixedBlockingExecutor(2);
        FixedBlockingExecutor pageExecutor = new FixedBlockingExecutor(2);
        Spider spider = SpiderBuilder.newBuilder(fetcherFactory, handler)
                .setRequestExecutor(requestExecutor)
                .setPageExecutor(pageExecutor)
                .build();

        Request request = new Request("https://www.baidu.com")
                .setFetcherType(FetcherType.FX_WEBVIEW)
                .setTimeout(5)
                .setRequestScripts(Lists.newArrayList(
                        new RequestScript("document.getElementById('kw').value = '计算机网络'", 1),
                        new RequestScript("document.getElementById('su').click()", 3)
                ));
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
