package com.diwayou.lang.string;

public class TrieStudy {
    public static void main(String[] args) {
        String name = "预售品：1月5日后支持发货或到柜提货】兰蔻新清滢柔肤水400ml 口碑大粉水";
        for (char c : name.toCharArray()) {
            System.out.println(c);
        }
    }
}
