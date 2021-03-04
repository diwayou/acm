package com.diwayou.code.city;

import com.diwayou.util.Json;
import com.diwayou.web.store.LevelDbConfig;
import com.diwayou.web.store.LevelDbStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.rocksdb.RocksDBException;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static com.diwayou.code.city.FetchCity.Row;
import static com.diwayou.code.city.FetchCity.RowType;

/**
 * @author gaopeng
 * @date 2020/4/21
 */
@Slf4j
public class ProcessCity {
    public static void main(String[] args) throws RocksDBException, IOException {
        Map<String, String> selfCity = Map.of(
                "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/11.html", "北京市",
                "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/12.html", "天津市",
                "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/31.html", "上海市",
                "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/50.html", "重庆市"
        );
        Map<String, String> province = Map.ofEntries(
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/13.html", "河北省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/14.html", "山西省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/15.html", "内蒙古自治区"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/21.html", "辽宁省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/22.html", "吉林省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/23.html", "黑龙江省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/31.html", "上海市"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/32.html", "江苏省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/33.html", "浙江省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/34.html", "安徽省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/35.html", "福建省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/36.html", "江西省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/37.html", "山东省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/41.html", "河南省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/42.html", "湖北省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/43.html", "湖南省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/44.html", "广东省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/45.html", "广西壮族自治区"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/46.html", "海南省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/51.html", "四川省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/52.html", "贵州省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/53.html", "云南省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/54.html", "西藏自治区"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/61.html", "陕西省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/62.html", "甘肃省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/63.html", "青海省"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/64.html", "宁夏回族自治区"),
                Map.entry("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/65.html", "新疆维吾尔自治区")
        );

        boolean isSql = true;
        LevelDbConfig config = new LevelDbConfig()
                .setUseExisting(true)
                .setSync(true);

        String fileName;
        if (isSql) {
            fileName = "province.sql";
        } else {
            fileName = "province.csv";
        }
        try (LevelDbStore cache = new LevelDbStore(Path.of("cache").toFile(), config);
             Writer writer = Files.newBufferedWriter(Path.of(fileName))) {
            processSelfCity(selfCity, cache, writer, isSql);
            processProvince(province, cache, writer, isSql);
        }
    }

    private static int curId = 1;

    private static void processProvince(Map<String, String> province, LevelDbStore cache, Writer writer, boolean isSql) throws IOException, RocksDBException {
        for (Map.Entry<String, String> entry : province.entrySet()) {
            String url = entry.getKey();
            String name = entry.getValue();

            int id = curId++;
            writeLine(writer, new Row(genProvinceCode(url), name, RowType.province.getName(), url), id, 0, isSql);

            doProcess(url, cache, writer, id, 2, false, isSql);
        }
    }

    private static String genProvinceCode(String href) {
        int sep = href.lastIndexOf(".");
        return StringUtils.rightPad(href.substring(sep - 2, sep), 12, '0');
    }

    private static void processSelfCity(Map<String, String> selfCity, LevelDbStore cache, Writer writer, boolean isSql) throws IOException, RocksDBException {
        for (Map.Entry<String, String> entry : selfCity.entrySet()) {
            String url = entry.getKey();
            String name = entry.getValue();

            int id = curId++;
            writeLine(writer, new Row(genProvinceCode(url), name, RowType.city.getName(), url), id, 0, isSql);

            doProcess(url, cache, writer, id, 2, true, isSql);
        }
    }

    private static void writeLine(Writer writer, Row row, int id, int parentId, boolean isSql) throws IOException {
        String line;
        if (!isSql) {
            line = String.format("%d,%s,%s,%s,%d\n", id, row.getCode(), row.getName(), row.getType(), parentId);
        } else {
            String tableName = "province";
            line = String.format("insert into `base`.`%s` (`%s`,`%s`,`%s`,`%s`,`%s`,`create_time`) VALUES ('%d','%s','%s','%s','%d',NOW());\n",
                    tableName, "id", "code", "name", "type", "parent_id",
                    id, row.getCode(), row.getName(), row.getType(), parentId);
        }
        writer.write(line);
    }

    private static void doProcess(String url, LevelDbStore cache, Writer writer, int parentId, int level, boolean isSelfCity, boolean isSql) throws IOException, RocksDBException {
        if (StringUtils.isBlank(url)) {
            return;
        }

        final int ns = 1;
        byte[] key = url.getBytes(StandardCharsets.UTF_8);
        byte[] content = cache.get(ns, key);
        if (content == null) {
            log.error("can't be null {}", url);
            return;
        }

        List<Row> rows = Json.fromJsonToList(new String(content, StandardCharsets.UTF_8), Row.class);

        for (Row row : rows) {
            if (!isSelfCity && StringUtils.isBlank(row.getUrl())) {
                log.info("ignore {}", row);
                continue;
            }

            int id = parentId;
            if (!isSelfCity || level != 2) {
                id = curId++;
                writeLine(writer, row, id, parentId, isSql);
            } else {
                log.info("ignore {}", row);
            }

            // 只处理town及以上级别
            if (row.getType().equals(RowType.town.getName())) {
                continue;
            }

            doProcess(row.getUrl(), cache, writer, id, level + 1, isSelfCity, isSql);
        }
    }
}
