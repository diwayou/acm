package com.diwayou.acm.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Robot {
    public static void main(String[] args) {
        ChromeInitUtil.setDriverPath("D:\\opensource\\chromedriver.exe");

        HttpRobot robot = new HttpRobot();
        String content = robot.get("http://m.51tiangou.com");
        Document document = Jsoup.parse(content);

        document.select("img[src]").stream()
                .map(e -> e.attr("src"))
                .filter(s -> !s.contains("log"))
                .forEach(System.out::println);
    }
}
