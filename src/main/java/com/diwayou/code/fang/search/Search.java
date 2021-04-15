package com.diwayou.code.fang.search;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.beust.jcommander.internal.Lists;
import com.diwayou.util.ExcelUtil;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
public class Search {

    private static WebClient webClient;

    public static final String NUM_KEY = "N";
    public static final String LOCATION_KEY = "L";
    public static final String COUNT_KEY = "C";

    private static final List<HouseInfo> HOUSE_INFO_ALL = Collections.synchronizedList(Lists.newArrayList());

    private static void init() {
        webClient = WebClient.builder()
                .baseUrl("http://124.93.228.101:8087/bd/tgxm/LAjax").build();
    }

    public static void main(String[] args) throws Exception {
        init();
        int[] stepIds = new int[]{711360648, 711365171, 711465824};

        List<BuildingInfo> allBuildingInfo = Lists.newArrayList();
        for (int stepId : stepIds) {
            List<BuildingInfo> buildingInfos = fetchBuildingInfo(stepId);

            allBuildingInfo.addAll(buildingInfos);
        }

        int page = allBuildingInfo.size();
        CountDownLatch latch = new CountDownLatch(page);
        Spider spider = SpiderBuilder.newBuilder(Path.of("sanding"))
                .setCrawlThreadNum(4)
                .setPageHandler(new PageHandler() {
                    @Override
                    public void handle(Page page, Spider spider) {
                        if (page.statusCode() != 200) {
                            latch.countDown();
                            log.warn(String.format("拉取%s不是200状态", page.getRequest().getUrl()));
                            return;
                        }

                        String content = page.bodyAsString();
                        Document document = Jsoup.parse(content);

                        final String num = (String) page.getRequest().getAttribute(NUM_KEY);
                        final String location = (String) page.getRequest().getAttribute(LOCATION_KEY);
                        final String count = (String) page.getRequest().getAttribute(COUNT_KEY);

                        document.select(".FCtable tr")
                                .forEach(el -> {
                                    List<HouseInfo> houseInfos = el.select("td").stream()
                                            .skip(1)
                                            .map(elm -> parseInfo(elm, num, location, count))
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList());

                                    HOUSE_INFO_ALL.addAll(houseInfos);
                                });

                        latch.countDown();
                        log.info("剩余{},{}", latch.getCount(), page.getRequest().getUrl());
                    }
                })
                .build();

        String urlTemplate = "http://124.93.228.101:8087/bd/tgxm/getLi?lid=%s";
        Scanner in = new Scanner(System.in);
        for (BuildingInfo buildingInfo : allBuildingInfo) {
            Request request = new Request(String.format(urlTemplate, buildingInfo.getId()))
                    .setFetcherType(FetcherType.JavaHttp)
                    .setTimeout(8)
                    .addAttribute(NUM_KEY, buildingInfo.getNum())
                    .addAttribute(LOCATION_KEY, buildingInfo.getLocation())
                    .addAttribute(COUNT_KEY, buildingInfo.getHouseCount());
            spider.submitRequest(request);

            //in.nextLine();
        }

        latch.await();

        ExcelUtil.write(HouseInfo.class, HOUSE_INFO_ALL, Files.newOutputStream(Path.of("sanding" + ExcelTypeEnum.XLSX.getValue())));

        spider.waitUntilStop();
    }

    private static List<BuildingInfo> fetchBuildingInfo(int stepId) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("xmid", String.valueOf(stepId));
        formData.add("pageNo", "1");
        formData.add("pageSize", "20");
        String content = webClient.post()
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        Document document = Jsoup.parse(content);

        List<BuildingInfo> buildingInfos = document.select("tr").stream()
                .skip(1)
                .map(el -> parseBuildingInfo(el))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return buildingInfos;
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

        return new BuildingInfo()
                .setNum(num)
                .setLocation(location)
                .setHouseCount(houseCount)
                .setId(id);
    }

    private static int getPage(int[][] idRange) {
        int result = 0;
        for (int[] range : idRange) {
            result += range[1] - range[0] + 1;
        }

        return result;
    }

    private static HouseInfo parseInfo(Element element, String num, String location, String count) {
        String level = element.text();
        if (StringUtils.isBlank(level)) {
            return null;
        }

        String info = element.attr("title");
        int miIdx = info.lastIndexOf("平方米");
        int commaIdx = info.lastIndexOf("：");
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
        log.info("{}", houseInfo);

        return houseInfo;
    }
}
