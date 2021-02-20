package com.diwayou.code.gaokao;

import com.beust.jcommander.internal.Lists;
import com.diwayou.util.Json;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author gaopeng
 * @date 2020/8/6
 */
@Slf4j
public class FetchTitle {

    public static void main(String[] args) throws IOException {
        String url = "http://www.dlymgz.cn/newsdisplay.do?id=%d";

        List<Title> titles = Lists.newArrayList();
        for (int i = 228; i < 4600; i++) {
            String title = fetchTitle(String.format(url, i));
            if (title != null) {
                log.info("{} {}", i, title);
                titles.add(new Title(i, title));
            }
        }

        Files.writeString(Path.of("titles.json"), Json.nonNull().toJson(titles));
    }

    private static String fetchTitle(String url) {
        Document document = null;
        try {
            Fetcher fetcher = FetcherFactory.one().getJavaHttpFetcher();
            Page page = fetcher.fetch(new Request(url));
            document = Jsoup.parse(page.bodyAsString(), page.getRequest().getUrl());

            String title = document.select(".title").text();

            return title;
        } catch (Exception e) {
            log.warn("", e);
            return null;
        }
    }
}
