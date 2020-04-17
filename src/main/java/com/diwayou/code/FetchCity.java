package com.diwayou.code;

import com.alibaba.fastjson.JSON;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.store.MemoryUrlStore;
import com.diwayou.web.store.UrlStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 爬取2020年的地址编码
 * http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html
 * @author gaopeng
 * @date 2020/4/17
 */
@Slf4j
public class FetchCity {
    public static void main(String[] args) throws IOException {
        UrlStore urlStore = new MemoryUrlStore();

        Fetcher fetcher = FetcherFactory.one().getFxWebviewFetcher();
        Page page = fetcher.fetch(new Request("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html"));
        //Document document = Jsoup.connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html").get();
        Document document = Jsoup.parse(page.bodyAsString(), page.getRequest().getUrl());

        List<Element> provinces = document.select("a").stream()
                .limit(31)
                .collect(Collectors.toList());

        for (Element pe : provinces) {
            String fileName = pe.text() + ".json";

            if (Files.exists(Path.of(fileName))) {
                continue;
            }

            log.info("fetch {}", fileName);

            String url = pe.absUrl("href");

            List<Row> province = new LinkedList<>();
            fetch(url, urlStore, province);

            if (!province.isEmpty()) {
                Files.writeString(Path.of(fileName), JSON.toJSONString(province), StandardCharsets.UTF_8);
            }
        }
    }

    @Data
    @AllArgsConstructor
    private static class Row {
        private String code;

        private String name;

        private String url;
    }

    private static void fetch(String url, UrlStore urlStore, List<Row> result) throws IOException {
        if (urlStore.contain(url)) {
            return;
        }

        urlStore.add(url);

        Document document = null;
        do {
            try {
                Fetcher fetcher = FetcherFactory.one().getFxWebviewFetcher();
                Page page = fetcher.fetch(new Request(url));
                document = Jsoup.parse(page.bodyAsString(), page.getRequest().getUrl());
            } catch (Exception e) {
                // ignore
            }
        } while (document == null);

       List<Row> rows = getNormal(document, ".countytr");
        if (rows.isEmpty()) {
            rows = getNormal(document, ".citytr");
            if (rows.isEmpty()) {
                rows = getNormal(document, ".towntr");

                if (rows.isEmpty()) {
                    rows = getVillage(document);
                    result.addAll(rows);

                    return;
                }
            }
        }

        for (Row row : rows) {
            result.add(row);
            log.info("fetch {}", row);

            fetch(row.getUrl(), urlStore, result);
        }
    }

    private static List<Row> getNormal(Document document, String select) {

        return document.select(select).stream()
                .map(e -> {
                    List<Element> row = new ArrayList<>(e.select("a"));
                    if (row.size() != 2) {
                        row = new ArrayList<>(e.select("td"));
                    }
                    if (row.size() != 2) {
                        log.warn("Row size error url={}", document.baseUri());
                        return null;
                    }

                    return new Row(row.get(0).text(), row.get(1).text(), row.get(1).absUrl("href"));
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static List<Row> getVillage(Document document) {

        return document.select(".villagetr").stream()
                .map(e -> {
                    List<Element> row = new ArrayList<>(e.select("td"));
                    if (row.size() != 3) {
                        log.warn("Row size error village url={}", document.baseUri());
                        return null;
                    }

                    return new Row(row.get(0).text(), row.get(2).text(), row.get(1).text());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
