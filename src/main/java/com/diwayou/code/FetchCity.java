package com.diwayou.code;

import com.diwayou.util.Json;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.store.LevelDbConfig;
import com.diwayou.web.store.LevelDbStore;
import com.diwayou.web.store.MemoryUrlStore;
import com.diwayou.web.store.UrlStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.rocksdb.RocksDBException;

import java.io.IOException;
import java.io.Writer;
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
 *
 * @author gaopeng
 * @date 2020/4/17
 */
@Slf4j
public class FetchCity {
    public static void main(String[] args) throws IOException, RocksDBException {
        UrlStore urlStore = new MemoryUrlStore();

        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html";

        Fetcher fetcher = FetcherFactory.one().getFxWebviewFetcher();
        Page page = fetcher.fetch(new Request(url));
        //Document document = Jsoup.connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html").get();
        Document document = Jsoup.parse(page.bodyAsString(), page.getRequest().getUrl());

        List<Element> provinces = new ArrayList<>(document.select("td > a"));

        LevelDbConfig config = new LevelDbConfig()
                .setUseExisting(true)
                .setSync(true);

        int count = 0;
        boolean save = false;
        try (LevelDbStore cache = new LevelDbStore(Path.of("cache").toFile(), config);
                Writer writer = Files.newBufferedWriter(Path.of("city.csv"))) {
            for (Element pe : provinces) {
                String provinceName = pe.text();

                log.info("fetch {}", provinceName);

                String provinceCode = genProvinceCode(pe.attr("href"));
                if (save) {
                    writer.write(String.format("%s,%s,%s\n", provinceCode, pe.text(), "province"));
                }

                List<Row> province = new LinkedList<>();
                int level = 1;
                fetch(pe.absUrl("href"), urlStore, province, cache, level);

                if (!province.isEmpty()) {
                    count += province.size();

                    for(Row row : province) {
                        if (save) {
                            writer.write(String.format("%s,%s,%s\n", row.getCode(), row.getName(), row.getType()));
                        }
                    }
                }
            }

            log.info("共抓取到{}条记录", count);
        } finally {
            FetcherFactory.one().close();
        }
    }

    private static String genProvinceCode(String href) {
        return StringUtils.rightPad(href.substring(0, href.indexOf(".")), 12, '0');
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Row {

        private String code;

        private String name;

        private String type;

        private String url;
    }

    @AllArgsConstructor
    @Getter
    public enum RowType {

        province("province", ".provincetr"),
        county("county", ".countytr"),
        city("city", ".citytr"),
        town("town", ".towntr"),
        village("village", ".villagetr");

        private final String name;

        private final String select;
    }

    private static void fetch(String url, UrlStore urlStore, List<Row> result, LevelDbStore cache, int level) throws IOException, RocksDBException {
        if (StringUtils.isBlank(url)) {
            return;
        }

        if (urlStore.contain(url)) {
            return;
        }

        urlStore.add(url);

        final int ns = 1;
        byte[] key = url.getBytes(StandardCharsets.UTF_8);
        byte[] content = cache.get(ns, key);
        List<Row> rows;
        if (content == null) {
            log.info("cache miss {}", url);
            rows = getRows(url);

            if (rows.isEmpty()) {
                return;
            }

            cache.write(wb -> wb.put(ns, key, Json.nonNull().toBytes(rows)));
        } else {
            rows = Json.nonNull().fromJsonToList(new String(content, StandardCharsets.UTF_8), Row.class);
        }
        if (!rows.isEmpty() && rows.get(0).type.equals(RowType.village.name)) {
            result.addAll(rows);

            return;
        }

        for (Row row : rows) {
            result.add(row);

            // 只抓取town以上级别
            if (row.getType().equals(RowType.town.getName())) {
                //continue;
            }

            fetch(row.getUrl(), urlStore, result, cache, level + 1);
        }
    }

    private static List<Row> getRows(String url) {
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

        List<Row> rows = getNormal(document, RowType.county);
        if (rows.isEmpty()) {
            rows = getNormal(document, RowType.city);
            if (rows.isEmpty()) {
                rows = getNormal(document, RowType.town);

                if (rows.isEmpty()) {
                    rows = getVillage(document);
                }
            }
        }

        if (rows.isEmpty()) {
            log.warn("getRows empty url={}", url);
        }

        return rows;
    }

    private static List<Row> getNormal(Document document, RowType rowType) {
        return document.select(rowType.getSelect()).stream()
                .map(e -> {
                    List<Element> row = new ArrayList<>(e.select("a"));
                    if (row.size() != 2) {
                        row = new ArrayList<>(e.select("td"));
                    }
                    if (row.size() != 2) {
                        log.warn("Row size error url={}", document.baseUri());
                        return null;
                    }

                    return new Row(row.get(0).text(), row.get(1).text(),
                            rowType.getName(), row.get(1).absUrl("href"));
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static List<Row> getVillage(Document document) {
        return document.select(RowType.village.getSelect()).stream()
                .map(e -> {
                    List<Element> row = new ArrayList<>(e.select("td"));
                    if (row.size() != 3) {
                        log.warn("Row size error village url={}", document.baseUri());
                        return null;
                    }

                    return new Row(row.get(0).text(), row.get(2).text(),
                            RowType.village.getName(),row.get(1).text());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
