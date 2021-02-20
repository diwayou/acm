package com.diwayou.code.fang;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.beust.jcommander.internal.Lists;
import com.diwayou.util.ExcelUtil;
import com.diwayou.util.Json;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.FetcherFactory;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
public class Beike {

    public static void main(String[] args) throws Exception {
        List<HouseInfoStandard> result = Collections.synchronizedList(Lists.newArrayList());

        // https://dl.ke.com/ershoufang/diwujun/pg3/?sug=%E7%AC%AC%E4%BA%94%E9%83%A1
        String sug = "第五郡";
        String urlTemplate = "https://dl.ke.com/ershoufang/diwujun/pg%d/?sug=%s";
        int page = getPage(String.format(urlTemplate, 1, sug));
        CountDownLatch latch = new CountDownLatch(page);
        Spider spider = SpiderBuilder.newBuilder(Path.of("beike"))
                .setCrawlThreadNum(2)
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

                        String title = document.title();

                        List<HouseInfo> houseInfoList = document.select(".sellListContent li .info").stream()
                                .map(Beike::parseInfo)
                                .collect(Collectors.toList());

                        List<HouseInfoStandard> houseInfoStandards = houseInfoList.stream()
                                .map(Beike::toStandard)
                                .collect(Collectors.toList());
                        result.addAll(houseInfoStandards);

                        System.out.println(title);
                        System.out.println(page.getRequest().getUrl());
                        houseInfoList.forEach(System.out::println);

                        latch.countDown();
                    }
                })
                .build();

        Scanner in = new Scanner(System.in);
        for (long l = 1; l <= page; l += 1) {
            Request request = new Request(String.format(urlTemplate, l, sug))
                    .setFetcherType(FetcherType.JavaHttp)
                    .setTimeout(2);
            spider.submitRequest(request);

            //in.nextLine();
        }

        latch.await();

        ExcelUtil.write(HouseInfoStandard.class, result, Files.newOutputStream(Path.of(sug + ExcelTypeEnum.XLSX.getValue())));

        spider.waitUntilStop();
    }

    private static int getPage(String url) {
        Request request = new Request(url)
                .setFetcherType(FetcherType.JavaHttp)
                .setTimeout(20);
        Page page = FetcherFactory.one().getJavaHttpFetcher().fetch(request);
        if (page.statusCode() != 200) {
            log.warn(String.format("拉取%s不是200状态", page.getRequest().getUrl()));
            throw new RuntimeException("获取总页数失败");
        }

        String content = page.bodyAsString();
        Document document = Jsoup.parse(content);

        String pageJson = document.select(".house-lst-page-box").first().attr("page-data");

        Map<String, Integer> data = Json.nonNull().fromJson(pageJson, Map.class);

        return data.get("totalPage");
    }

    private static HouseInfo parseInfo(Element element) {
        Element elTitle = element.select(".title a").first();
        String title = elTitle.text();
        String url = elTitle.attr("href");

        Element elPosition = element.select(".address").first();
        String positionInfo = elPosition.select(".positionInfo").text();
        String baseInfo = elPosition.select(".houseInfo").text();
        String followInfo = elPosition.select(".followInfo").text();
        String totalPrice = elPosition.select(".totalPrice").text();
        String unitPrice = elPosition.select(".unitPrice").text();

        return new HouseInfo()
                .setTitle(title)
                .setUrl(url)
                .setPositionInfo(positionInfo)
                .setBaseInfo(baseInfo)
                .setFollowInfo(followInfo)
                .setTotalPrice(totalPrice)
                .setUnitPrice(unitPrice);
    }

    private static HouseInfoStandard toStandard(HouseInfo houseInfo) {
        HouseInfoStandard result = new HouseInfoStandard()
                .setTitle(houseInfo.getTitle())
                .setUrl(houseInfo.getUrl())
                .setPositionInfo(houseInfo.getPositionInfo())
                .setBaseInfo(houseInfo.getBaseInfo());

        try {
            String[] followInfo = houseInfo.getFollowInfo().split("/");
            String followNum = followInfo[0].trim();
            result.setFollowNum(Integer.parseInt(followNum.substring(0, followNum.indexOf("人"))));
            result.setPublishTime(followInfo[1].trim());

            result.setTotalPrice(Double.parseDouble(houseInfo.getTotalPrice().substring(0, houseInfo.getTotalPrice().indexOf(" "))));

            String unitPrice = houseInfo.getUnitPrice().substring(2, houseInfo.getUnitPrice().indexOf("元"));
            result.setUnitPrice(Integer.parseInt(unitPrice));
        } catch (Exception e) {
            log.warn("", e);
        }

        return result;
    }
}
