package com.diwayou.web.app.example;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;
import com.diwayou.web.domain.RequestScript;
import com.google.common.collect.Lists;

import java.nio.file.Path;
import java.util.List;

public class TmallItemDetail {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("D:/tmp"))
                .setCrawlThreadNum(1)
                .setScriptsPath(Path.of(ClassLoader.getSystemResource("scripts").toURI()))
                .build();

        List<RequestScript> scripts = Lists.newArrayList();
        for (int i = 1000; i <= 21000; i += 3000) {
            scripts.add(new RequestScript(String.format("window.scrollTo(0, %d)", i), 1));
        }
        Request request = new Request("https://detail.tmall.com/item.htm?id=522216409648")
                .setFetcherType(FetcherType.FX_WEBVIEW)
                .setTimeout(2)
                .setMaxDepth(2)
                .setRequestScripts(scripts);
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
