package com.diwayou.web.url;

import org.apache.commons.io.FilenameUtils;

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

    public static boolean isImage(String url) {
        String ext = FilenameUtils.getExtension(url);

        return ext.equalsIgnoreCase("jpg") ||
                ext.equalsIgnoreCase("png") ||
                ext.equalsIgnoreCase("jpeg") ||
                ext.equalsIgnoreCase("bmp") ||
                ext.equalsIgnoreCase("tif") ||
                ext.equalsIgnoreCase("tiff") ||
                ext.equalsIgnoreCase("gif");
    }

    public static boolean hasExtension(String url) {
        return !FilenameUtils.getExtension(url).isBlank();
    }
}
