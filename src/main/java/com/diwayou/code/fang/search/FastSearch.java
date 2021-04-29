package com.diwayou.code.fang.search;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.diwayou.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ReactorNetty;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class FastSearch {

    private static WebClient webClient;

    private static void init() {
        System.setProperty(ReactorNetty.IO_SELECT_COUNT, "" + Math.max(Runtime.getRuntime()
                .availableProcessors(), 4));
        System.setProperty(ReactorNetty.IO_WORKER_COUNT, "20");

        webClient = WebClient.builder()
                .baseUrl("http://124.93.228.101:8087/bd/tgxm/").build();
    }

    public static void main(String[] args) throws Exception {
        init();
        // B区
        //Integer[] stepIds = new Integer[]{711360648, 711365171, 711465824};
        // C区
        Integer[] stepIds = new Integer[]{712127954, 712143729};
        String filename = "sanding-c";

        List<HouseInfo> result = Flux.merge(Flux.fromArray(stepIds)
                .map(FastSearch::fetchBuildingInfo)
                .map(FastSearch::fetchHouse))
                .flatMap(Flux::fromIterable)
                .collectList()
                .block();

        ExcelUtil.write(HouseInfo.class, result, Files.newOutputStream(Path.of(filename + ExcelTypeEnum.XLSX.getValue())));
    }

    private static Mono<List<BuildingInfo>> fetchBuildingInfo(int stepId) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("xmid", String.valueOf(stepId));
        formData.add("pageNo", "1");
        formData.add("pageSize", "50");

        return webClient.post()
                .uri("/LAjax")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .map(Jsoup::parse)
                .map(doc -> doc.select("tr").stream()
                        .skip(1)
                        .map(FastSearch::parseBuildingInfo)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    private static Flux<List<HouseInfo>> fetchHouse(Mono<List<BuildingInfo>> buildings) {
        return Flux.merge(buildings.map(houseInfos ->
                houseInfos.stream()
                        .map(info -> webClient.get()
                                .uri("/getLi?lid={id}", info.getId())
                                .retrieve()
                                .bodyToMono(String.class)
                                .map(s -> Tuples.of(info, s)))
                        .map(FastSearch::parsePage)
                        .collect(Collectors.toList()))
                .map(Flux::merge));
    }

    private static Mono<List<HouseInfo>> parsePage(Mono<Tuple2<BuildingInfo, String>> body) {
        return body.map(t2 -> {
            Document doc = Jsoup.parse(t2.getT2());
            BuildingInfo buildingInfo = t2.getT1();

            return doc.select(".FCtable tr").stream()
                    .map(el -> el.select("td").stream()
                            .skip(1)
                            .map(elm -> parseInfo(elm, buildingInfo.getNum(), buildingInfo.getLocation(), buildingInfo.getHouseCount()))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        });
    }

    private static BuildingInfo parseBuildingInfo(Element el) {
        Elements els = el.select("td");
        if (els.size() < 4) {
            return null;
        }
        String num = els.get(0).text();
        String location = els.get(2).text();
        String houseCount = els.get(3).text();

        String url = els.get(0).select("a").get(0).attr("href");
        String id = url.substring(url.lastIndexOf("=") + 1);

        BuildingInfo buildingInfo = new BuildingInfo()
                .setNum(num)
                .setLocation(location)
                .setHouseCount(houseCount)
                .setId(id);

//        log.info("{} {}", Thread.currentThread().getName(), buildingInfo);

        return buildingInfo;
    }

    private static HouseInfo parseInfo(Element element, String num, String location, String count) {
        String level = element.text();
        if (StringUtils.isBlank(level)) {
            return null;
        }

        String info = element.attr("title");
        int miIdx = info.length();
        if (info.contains("元")) {
            miIdx = info.lastIndexOf("平方米") - 1;
        }
        miIdx = info.lastIndexOf("平方米", miIdx);
        int commaIdx = info.lastIndexOf("：", miIdx);
        String innerArea = info.substring(commaIdx + 1, miIdx);
        miIdx = info.lastIndexOf("平方米", miIdx - 1);
        commaIdx = info.lastIndexOf("：", commaIdx - 1);
        String area = info.substring(commaIdx + 1, miIdx);
        HouseInfo houseInfo = new HouseInfo()
                .setNum(num)
                .setLocation(location)
                .setHouseCount(count)
                .setLevel(level)
                .setArea(area)
                .setInnerArea(innerArea)
                .setAreaPercent(String.format("%.2f", Double.parseDouble(innerArea) / Double.parseDouble(area) * 100));
//        log.info("{} {}", Thread.currentThread().getName(), houseInfo);

        return houseInfo;
    }
}
