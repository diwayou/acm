package com.diwayou.lang.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexStudy {
    public static void main(String[] args) {
        test("(?x) ^(http|https):// ([^/:]+) (?::(\\d+))?", "https://www.baidu.com:80/index");
        test("(?x) <.*?>", "<a>html</a>");
    }

    private static void test(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(matcher.group(i));
            }
        }

        System.out.println("-------------------------------------------------------------------");
    }
}
