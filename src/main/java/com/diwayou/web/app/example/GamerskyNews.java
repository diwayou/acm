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
public class GamerskyNews {
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
                        String meta = document.select(".detail").text();
                        String body = document.select(".Mid2L_con").text();

                        System.out.println(title);
                        System.out.println(meta);
                        System.out.println(page.getRequest().getUrl());
                        System.out.println(body);
                    }
                })
                .build();

        Scanner in = new Scanner(System.in);
        Random random = new Random();
        //https://db2.gamersky.com/LabelJsonpAjax.aspx?callback=jQuery183026332377974029253_1576220918171&jsondata={"type":"updatenodelabel","isCache":true,"cacheTime":60,"nodeId":"11007","isNodeId":"true","page":2}&_=1576221018029
        for (long l = 1247677; l > 1; l -= 1) {
            Request request = new Request(String.format("https://db2.gamersky.com/LabelJsonpAjax.aspx?callback=jQuery183026332377974029253_1576220918171&jsondata={\"type\":\"updatenodelabel\",\"isCache\":true,\"cacheTime\":60,\"nodeId\":\"11007\",\"isNodeId\":\"true\",\"page\":2}&_=1576221018029", l))
                    .setFetcherType(FetcherType.JAVA_HTTP)
                    .setTimeout(2);
            spider.submitRequest(request);

            in.nextLine();
        }

        spider.waitUntilStop();
    }
}
