package com.diwayou.code.gaokao.score;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.diwayou.util.ExcelUtil;
import com.diwayou.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.ReactorNetty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author gaopeng 2021/4/29
 */
@Slf4j
public class FetchGaoKaoInfo {

    // https://static-data.eol.cn/www/2.0/school/225/info.json
    private static final String infoUrl = "https://static-data.eol.cn/www/2.0/school/{id}/info.json";

    private static WebClient client;

    static {
        init();
    }

    private static void init() {
        System.setProperty(ReactorNetty.IO_SELECT_COUNT, "1");
        System.setProperty(ReactorNetty.IO_WORKER_COUNT, "1");

        client = WebClient.builder()
                .build();
    }

    public static void main(String[] args) throws IOException {
        List<School> schools = Flux.merge(Flux.range(1, 10000)
                .map(id -> client.get()
                        .uri(infoUrl, id)
                        .retrieve()
                        .bodyToMono(String.class))
                .map(ms -> ms.filter(s -> !s.equals("\"\""))
                        .map(s -> {
                            SchoolWrapper wrapper = Json.fromJson(s, SchoolWrapper.class);
                            if (wrapper == null) {
                                return null;
                            }
                            return wrapper.getData();
                        })).collectList()
                .map(Flux::merge))
                .collectList()
                .block();

        ExcelUtil.write(School.class, schools, Files.newOutputStream(Path.of("school" + ExcelTypeEnum.XLSX.getValue())));
    }
}
