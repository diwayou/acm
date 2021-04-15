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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
public class SanDing {

    public static void main(String[] args) throws Exception {
        List<HouseInfo> result = Collections.synchronizedList(Lists.newArrayList());

        String urlTemplate = "http://124.93.228.101:8087/bd/tgxm/getLi?lid=%d";
        int[][] idRange = new int[][]{
                new int[]{123871, 123871},
                new int[]{123873, 123881},
                new int[]{123899, 123902},
                new int[]{123904, 123912},
                new int[]{124257, 124267},
                new int[]{124269, 124272}
//                new int[]{124261, 124261}
        };
        int page = getPage(idRange);
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

                        String num = document.select("h3.text-center").text();

                        document.select(".FCtable tr")
                                .forEach(el -> {
                                    List<HouseInfo> houseInfos = el.select("td").stream()
                                            .skip(1)
                                            .map(elm -> parseInfo(elm, num))
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList());

                                    result.addAll(houseInfos);
                                });

                        latch.countDown();
                        log.info("剩余{},{}", latch.getCount(), page.getRequest().getUrl());
                    }
                })
                .build();

        Scanner in = new Scanner(System.in);
        for (int[] range : idRange) {
            for (int id = range[0]; id <= range[1]; id++) {
                Request request = new Request(String.format(urlTemplate, id))
                        .setFetcherType(FetcherType.JavaHttp)
                        .setTimeout(8);
                spider.submitRequest(request);

                //in.nextLine();
            }
        }

        latch.await();

        ExcelUtil.write(HouseInfo.class, result, Files.newOutputStream(Path.of("sanding" + ExcelTypeEnum.XLSX.getValue())));

        spider.waitUntilStop();
    }

    private static int getPage(int[][] idRange) {
        int result = 0;
        for (int[] range : idRange) {
            result += range[1] - range[0] + 1;
        }

        return result;
    }

    private static HouseInfo parseInfo(Element element, String num) {
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
        HouseInfo houseInfo =  new HouseInfo()
                .setNum(num)
                .setLevel(level)
                .setArea(area)
                .setInnerArea(innerArea);
        log.info("{}", houseInfo);

        return houseInfo;
    }
}
