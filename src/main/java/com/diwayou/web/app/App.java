package com.diwayou.web.app;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Request;
import com.diwayou.web.domain.RequestScript;
import com.google.common.collect.Lists;

import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("D:/tmp"))
                .setCrawlThreadNum(1)
                .setScriptsPath(Path.of(ClassLoader.getSystemResource("scripts").toURI()))
                .build();

        Request request = new Request("https://www.baidu.com")
                .setFetcherType(FetcherType.FX_WEBVIEW)
                .setTimeout(2)
                .setRequestScripts(Lists.newArrayList(
                        new RequestScript("document.getElementById('kw').value = '计算机网络'", 1),
                        new RequestScript("document.getElementById('su').click()", 3)
                ));
        spider.submitRequest(request);

        spider.waitUntilStop();
    }
}
