package com.diwayou.web.app.example;

import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.FetcherFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.file.Path;
import java.util.Scanner;

@Slf4j
public class CnbetaNewsAuto {
    public static void main(String[] args) throws Exception {
        Spider spider = SpiderBuilder.newBuilder(Path.of("cnbeta"))
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
                        String body = document.select("#artibody > p").text();

                        System.out.printf("%s %s %s\n", title, page.getRequest().getUrl(), meta);
                        System.out.println(body.substring(0, Math.min(280, body.length())));
                    }
                })
                .build();


        Request request = new Request("https://www.cnbeta.com")
                .setFetcherType(FetcherType.JavaHttp)
                .setTimeout(20);
        Page page = FetcherFactory.one().getJavaHttpFetcher().fetch(request);
        if (page.statusCode() != 200) {
            log.warn(String.format("拉取%s不是200状态", page.getRequest().getUrl()));
            return;
        }

        String content = page.bodyAsString();
        Document document = Jsoup.parse(content);

        String maxId = document.select(".items-area .item").next().next().next().attr("id");

        Scanner in = new Scanner(System.in);
        for (long l = Long.parseLong(maxId.substring(maxId.lastIndexOf('_') + 1)); l > 900000; l -= 2) {
            request = new Request(String.format("https://www.cnbeta.com/articles/tech/%d.htm", l))
                    .setFetcherType(FetcherType.JavaHttp)
                    .setTimeout(3);
            spider.submitRequest(request);

            String line = in.nextLine();
            if (StringUtils.isNumeric(line) && !line.isBlank()) {
                l -= Long.parseLong(line);
            }
        }

        spider.waitUntilStop();
    }
}
