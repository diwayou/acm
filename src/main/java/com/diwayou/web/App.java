package com.diwayou.web;

import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        PageHandler handler = new PageHandler() {
            @Override
            public boolean interest(Request request) {
                return true;
            }

            @Override
            public void handle(Page page, Spider spider) {
                System.out.println("访问到 " + page.getRequest().getUrl());
            }
        };

        Spider spider = SpiderBuilder.newBuilder(Arrays.asList(new Request("https://www.qq.com/").setTimeout(10)), handler)
                .build();

        spider.startAndWait();
    }
}
