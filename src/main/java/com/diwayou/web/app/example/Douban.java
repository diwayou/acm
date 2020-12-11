package com.diwayou.web.app.example;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;

import java.nio.file.Path;

public class Douban {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("douban"))
                .setCrawlThreadNum(2)
                .build();

        Request request = new Request("https://www.douban.com")
                .setFetcherType(FetcherType.FxWebView)
                .setTimeout(3);
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
