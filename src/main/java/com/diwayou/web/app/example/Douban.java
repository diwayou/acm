package com.diwayou.web.app.example;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;

import java.nio.file.Path;

public class Douban {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("D:/tmp"))
                .setCrawlThreadNum(2)
                .setScriptsPath(Path.of(ClassLoader.getSystemResource("scripts").toURI()))
                .build();

        Request request = new Request("https://www.douban.com")
                .setFetcherType(FetcherType.FX_WEBVIEW)
                .setTimeout(3);
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
