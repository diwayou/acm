package com.diwayou.web.url;

import java.net.URI;

public class UrlUtil {

    public static String urlToFilename(String url) {
        if (url == null) {
            return "";
        }

        try {
            URI uri = URI.create(url);
            String host = uri.getHost();
            String path = uri.getPath();

            String filename = "" + host + "_" + path;
            filename = filename.replace("/", "_");

            return filename;
        } catch (Exception e) {
            return "";
        }
    }
}
