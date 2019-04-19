package com.diwayou.web.http;

import com.diwayou.web.http.robot.HttpRobot;
import com.diwayou.web.support.DocumentUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.html.HTMLDocument;

public class Robot {
    public static void main(String[] args) throws Exception {
        try (HttpRobot robot = new HttpRobot()) {
            getContent(robot);
        }
    }

    private static void getContent(HttpRobot robot) throws Exception {
        HTMLDocument content = robot.get("http://sports.qq.com/nba/", 10);
        Document document = Jsoup.parse(DocumentUtil.toString(content));

        document.select("a[href]").stream()
                .map(e -> e.attr("href"))
                .forEach(System.out::println);
    }
}
