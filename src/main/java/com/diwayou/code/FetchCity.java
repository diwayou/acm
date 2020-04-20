package com.diwayou.code;

import com.alibaba.fastjson.JSON;
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
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.rocksdb.RocksDBException;

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
 *
 * @author gaopeng
 * @date 2020/4/17
 */
@Slf4j
public class FetchCity {
    public static void main(String[] args) throws IOException, RocksDBException {
        UrlStore urlStore = new MemoryUrlStore();

        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html";

        // [[http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/11.html, 北京市],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/12.html, 天津市],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/13.html, 河北省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/14.html, 山西省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/15.html, 内蒙古自治区],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/21.html, 辽宁省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/22.html, 吉林省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/23.html, 黑龙江省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/31.html, 上海市],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/32.html, 江苏省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/33.html, 浙江省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/34.html, 安徽省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/35.html, 福建省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/36.html, 江西省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/37.html, 山东省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/41.html, 河南省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/42.html, 湖北省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/43.html, 湖南省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/44.html, 广东省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/45.html, 广西壮族自治区],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/46.html, 海南省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/50.html, 重庆市],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/51.html, 四川省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/52.html, 贵州省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/53.html, 云南省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/54.html, 西藏自治区],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/61.html, 陕西省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/62.html, 甘肃省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/63.html, 青海省],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/64.html, 宁夏回族自治区],
        // [http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/65.html, 新疆维吾尔自治区]]

        Fetcher fetcher = FetcherFactory.one().getFxWebviewFetcher();
        Page page = fetcher.fetch(new Request(url));
        //Document document = Jsoup.connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/index.html").get();
        Document document = Jsoup.parse(page.bodyAsString(), page.getRequest().getUrl());

        List<Element> provinces = document.select("a").stream()
                .limit(31)
                .collect(Collectors.toList());

        LevelDbConfig config = new LevelDbConfig()
                .setUseExisting(true)
                .setSync(true);
        try (LevelDbStore cache = new LevelDbStore(Path.of("cache").toFile(), config)) {
            for (Element pe : provinces) {
                String fileName = pe.text() + ".json";

                if (Files.exists(Path.of(fileName))) {
                    continue;
                }

                log.info("fetch {}", fileName);

                List<Row> province = new LinkedList<>();
                fetch(pe.absUrl("href"), urlStore, province, cache);

                if (!province.isEmpty()) {
                    //Files.writeString(Path.of(fileName), JSON.toJSONString(province), StandardCharsets.UTF_8);
                }
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Row {

        private String code;

        private String name;

        private String type;

        private String url;
    }

    @AllArgsConstructor
    @Getter
    private enum RowType {

        county("county", ".countytr"),
        city("city", ".citytr"),
        town("town", ".towntr"),
        village("village", ".villagetr");

        private final String name;

        private final String select;
    }

    private static void fetch(String url, UrlStore urlStore, List<Row> result, LevelDbStore cache) throws IOException, RocksDBException {
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

            cache.write(wb -> {
                wb.put(ns, key, JSON.toJSONBytes(rows));
            });
        } else {
            rows = JSON.parseArray(new String(content, StandardCharsets.UTF_8), Row.class);
        }
        if (!rows.isEmpty() && rows.get(0).type.equals(RowType.village.name)) {
            result.addAll(rows);

            return;
        }

        for (Row row : rows) {
            result.add(row);

            fetch(row.getUrl(), urlStore, result, cache);
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
