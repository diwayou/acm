package com.diwayou.web.app.example;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;

import java.nio.file.Path;

public class DoubanPhoto {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("/Users/gaopeng/Downloads/douban"))
                .setCrawlThreadNum(2)
                .build();

        for (long l = 2576600000L; l < 2576698588L; l++) {
            Request request = new Request(String.format("https://img1.doubanio.com/view/photo/l/public/p%d.jpeg", l))
                    .setFetcherType(FetcherType.JAVA_HTTP)
                    .setTimeout(6);
            spider.submitRequest(request);
        }

        spider.waitUntilStop();
    }
}
