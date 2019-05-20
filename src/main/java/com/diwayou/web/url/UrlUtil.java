package com.diwayou.web.url;

import com.diwayou.web.support.FilenameUtil;

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

    public static String getHost(String url) {
        URI uri = URI.create(url);
        String host = uri.getHost();
        if (host == null) {
            return null;
        }

        if (host.startsWith("www.")) {
            host = host.substring(4);
        }

        return host;
    }

    public static boolean isImage(String url) {
        String ext = FilenameUtil.getExt(url);

        return ext.equalsIgnoreCase("jpg") ||
                ext.equalsIgnoreCase("png") ||
                ext.equalsIgnoreCase("jpeg") ||
                ext.equalsIgnoreCase("bmp") ||
                ext.equalsIgnoreCase("tif") ||
                ext.equalsIgnoreCase("tiff") ||
                ext.equalsIgnoreCase("gif");
    }

    public static boolean hasExtension(String url) {
        return !FilenameUtil.getExt(url).isBlank();
    }
}
