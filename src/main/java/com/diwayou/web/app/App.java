package com.diwayou.web.app;

import com.diwayou.web.concurrent.FixedBlockingExecutor;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;
import com.diwayou.web.domain.Script;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.store.MemoryUrlStore;
import com.diwayou.web.store.UrlStore;
import com.google.common.collect.Lists;

public class App {
    public static void main(String[] args) throws Exception {
        UrlStore urlStore = new MemoryUrlStore();
        PageHandler handler = new CrawlPageHandler(urlStore);

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
                .setScripts(Lists.newArrayList(
                        new Script("document.getElementById('kw').value = '计算机网络'", 1),
                        new Script("document.getElementById('su').click()", 3)
                ));
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
