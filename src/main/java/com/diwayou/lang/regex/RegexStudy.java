package com.diwayou.lang.regex;

import java.util.regex.Pattern;

public class RegexStudy {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(".*国");
        System.out.println(pattern.matcher("中国").matches());
    }
}
