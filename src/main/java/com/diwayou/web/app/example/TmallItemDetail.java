package com.diwayou.web.app.example;

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
import com.diwayou.web.script.ScriptRegistry;
import com.diwayou.web.store.FilePageStore;
import com.diwayou.web.store.LucenePageStore;
import com.google.common.collect.Lists;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class TmallItemDetail {
    public static void main(String[] args) throws Exception {
        File dir = new File(ClassLoader.getSystemResource("scripts").toURI());
        ScriptRegistry.one().load(dir);

        FilePageStore filePageStore = new FilePageStore(new File("D:/tmp/image"));
        try (LucenePageStore lucenePageStore = new LucenePageStore(filePageStore, Path.of("D:/tmp/index"))) {
            CrawlConfig crawlConfig = new CrawlConfig()
                    .setMaxDepth(3)
                    .setPageStore(lucenePageStore);
            PageHandler handler = new ScriptCrawlPageHandler(crawlConfig);

            FetcherFactory fetcherFactory = new FetcherFactory();

            FixedBlockingExecutor requestExecutor = new FixedBlockingExecutor(2);
            FixedBlockingExecutor pageExecutor = new FixedBlockingExecutor(2);
            Spider spider = SpiderBuilder.newBuilder(fetcherFactory, handler)
                    .setRequestExecutor(requestExecutor)
                    .setPageExecutor(pageExecutor)
                    .build();

            List<RequestScript> scripts = Lists.newArrayList();
            for (int i = 1000; i <= 21000; i += 3000) {
                scripts.add(new RequestScript(String.format("window.scrollTo(0, %d)", i), 1));
            }
            Request request = new Request("https://detail.tmall.com/item.htm?id=522216409648")
                    .setFetcherType(FetcherType.FX_WEBVIEW)
                    .setTimeout(2)
                    .setRequestScripts(scripts);
            spider.submitRequest(request);

            spider.waitUntilStop();
        }
    }
}
