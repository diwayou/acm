package com.diwayou.web.http;

public class ChromeInitUtil {

    public static void setDriverPath(String path) {
        System.setProperty(Constant.CHROME_DRIVER, path);
    }
}
