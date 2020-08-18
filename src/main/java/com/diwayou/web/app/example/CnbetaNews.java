package com.diwayou.web.app.example;

import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

@Slf4j
public class CnbetaNews {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("/Users/gaopeng/Downloads/cnbeta"))
                .setCrawlThreadNum(2)
                .setPageHandler(new PageHandler() {
                    @Override
                    public void handle(Page page, Spider spider) {
                        if (page.statusCode() != 200) {
                            log.warn(String.format("拉取%s不是200状态", page.getRequest().getUrl()));
                            return;
                        }

                        String content = page.bodyAsString();
                        Document document = Jsoup.parse(content);

                        String title = document.title();
                        String meta = document.select(".meta").text();
                        String body = document.select("#artibody").text();

                        System.out.println(title);
                        System.out.println(meta);
                        System.out.println(page.getRequest().getUrl());
                        System.out.println(body);
                    }
                })
                .build();

        Scanner in = new Scanner(System.in);
        Random random = new Random();
        for (long l = 922531; l > 900000; l -= 2) {
            Request request = new Request(String.format("https://www.cnbeta.com/articles/tech/%d.htm", l))
                    .setFetcherType(FetcherType.JAVA_HTTP)
                    .setTimeout(2);
            spider.submitRequest(request);

            in.nextLine();
        }

        spider.waitUntilStop();
    }
}
