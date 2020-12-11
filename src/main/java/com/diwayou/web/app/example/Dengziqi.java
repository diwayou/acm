package com.diwayou.web.app.example;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;

import java.nio.file.Path;

public class Dengziqi {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("dzq"))
                .setCrawlThreadNum(5)
                .setTimeout(100)
                .build();

        Request request = new Request("http://geteverybodymoving.com/")
                .setFetcherType(FetcherType.JavaHttp)
                .setTimeout(100);
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
