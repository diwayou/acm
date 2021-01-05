package com.diwayou.web.app.example;

import com.diwayou.web.config.AppConfig;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;

import java.nio.file.Path;

public class UtadaHikaru {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("cuh"))
                .setCrawlThreadNum(20)
                .setTimeout(100)
                .build();

        AppConfig.setImageLengthLimit(50 * 1024);
        Request request = new Request("http://www.utadahikaru.jp/en/gallery/")
                .setFetcherType(FetcherType.JavaHttp)
                .setTimeout(100);
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
