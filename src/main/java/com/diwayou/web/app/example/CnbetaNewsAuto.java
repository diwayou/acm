package com.diwayou.web.app.example;

import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.log.AppLog;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CnbetaNewsAuto {
    private static final Logger log = AppLog.getCrawl();

    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("/Users/gaopeng/Downloads/cnbeta"))
                .setCrawlThreadNum(2)
                .setPageHandler(new PageHandler() {
                    @Override
                    public void handle(Page page, Spider spider) {
                        if (page.statusCode() != 200) {
                            log.log(Level.WARNING, String.format("拉取%s不是200状态", page.getRequest().getUrl()));
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


        Request request = new Request("https://www.cnbeta.com")
                .setFetcherType(FetcherType.JAVA_HTTP)
                .setTimeout(2);
        Page page = FetcherFactory.one().getJavaHttpFetcher().fetch(request);
        if (page.statusCode() != 200) {
            log.log(Level.WARNING, String.format("拉取%s不是200状态", page.getRequest().getUrl()));
            return;
        }

        String content = page.bodyAsString();
        Document document = Jsoup.parse(content);

        String maxId = document.select(".items-area .item").next().next().attr("id");

        Scanner in = new Scanner(System.in);
        for (long l = Long.parseLong(maxId.substring(maxId.lastIndexOf('_') + 1)); l > 900000; l -= 2) {
            request = new Request(String.format("https://www.cnbeta.com/articles/tech/%d.htm", l))
                    .setFetcherType(FetcherType.JAVA_HTTP)
                    .setTimeout(2);
            spider.submitRequest(request);

            String line = in.nextLine();
            if (StringUtils.isNumeric(line) && !line.isBlank()) {
                l -= Long.parseLong(line);
            }
        }

        spider.waitUntilStop();
    }
}
