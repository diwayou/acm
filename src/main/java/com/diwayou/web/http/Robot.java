package com.diwayou.web.http;

import com.diwayou.web.http.robot.HttpRobot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Robot {
    public static void main(String[] args) throws Exception {
        try (HttpRobot robot = new HttpRobot()) {
            getContent(robot);
        }
    }

    private static void getContent(HttpRobot robot) throws Exception {
        String content = robot.get("https://m.51tiangou.com", 3);
        Document document = Jsoup.parse(content);

        document.select("img[src]").stream()
                .map(e -> e.attr("src"))
                .filter(s -> !s.contains("log"))
                .forEach(System.out::println);
    }
}
