package com.diwayou.awt;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class FontStudy {
    public static void main(String[] args) {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fonts = environment.getAvailableFontFamilyNames();

        System.out.println(fonts.length);

        List<String> needs = Arrays.asList("Helvetica Neue","Helvetica","PingFang SC","Hiragino Sans GB","Microsoft YaHei","Arial","sans-serif");

        for (String font : fonts) {
            if (needs.indexOf(font) >= 0) {
                System.out.println(font);
            }
        }
    }
}
