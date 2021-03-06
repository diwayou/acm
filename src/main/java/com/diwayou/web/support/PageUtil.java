package com.diwayou.web.support;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PageUtil {

    public static boolean isHtml(Page page) {
        if (FetcherType.FxWebView.equals(page.getRequest().getFetcherType())) {
            return true;
        }

        String contentType = getContentType(page);
        if (contentType == null) {
            return false;
        }

        return contentType.contains("text") && contentType.contains("html");
    }

    public static boolean isImage(String contentType) {
        if (contentType == null) {
            return false;
        }

        return contentType.contains("image");
    }

    public static boolean isImage(Page page) {
        return isImage(getContentType(page));
    }

    public static boolean hasBinaryContent(Page page) {
        String contentType = getContentType(page);
        String typeStr = (contentType != null) ? contentType.toLowerCase() : "";

        return typeStr.contains("image") || typeStr.contains("audio") ||
                typeStr.contains("video") || typeStr.contains("application");
    }

    public static boolean hasPlainTextContent(Page page) {
        String contentType = getContentType(page);
        String typeStr = (contentType != null) ? contentType.toLowerCase() : "";

        return typeStr.contains("text") && !typeStr.contains("html");
    }

    public static boolean hasCssTextContent(Page page) {
        String contentType = getContentType(page);
        String typeStr = (contentType != null) ? contentType.toLowerCase() : "";

        return typeStr.contains("css");
    }

    public static Charset getCharset(Page page) {
        return getCharset(page, StandardCharsets.UTF_8);
    }

    public static Charset getCharset(Page page, Charset defaultCharset) {
        String contentType = getContentType(page);
        if (contentType == null) {
            return defaultCharset;
        }

        int idx = contentType.indexOf("=");

        if (idx > 0) {
            return Charset.forName(contentType.substring(idx + 1));
        }

        return defaultCharset;
    }

    public static String getExt(Page page) {
        String contentType = getContentType(page);
        String ext = "txt";
        if (contentType != null) {
            try {
                MediaType mediaType = MediaType.parse(contentType);
                ext = mediaType.subtype();
                if (ext == null || ext.isBlank()) {
                    ext = FilenameUtil.getExt(page.getRequest().getUrl());
                    if (ext.isBlank()) {
                        ext = "txt";
                    }
                }
            } catch (Exception ignore) {
            }
        }

        return ext;
    }

    public static String getContentType(Page page) {
        return page.getHttpHeaders()
                .firstValue(HttpHeaders.CONTENT_TYPE)
                .orElse(null);
    }

    public static long getContentLength(Page page) {
        return page.getHttpHeaders().
                firstValueAsLong(HttpHeaders.CONTENT_LENGTH)
                .orElse(0);
    }

    public static String humanReadableContentLength(Page page) {
        return humanReadableByteCount(getContentLength(page));
    }

    public static String humanReadableByteCount(long bytes) {
        int unit = 1024;

        if (bytes < unit) {
            return bytes + "B";
        }

        int exp = (int) (Math.log(bytes) / Math.log(unit));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f%cB", bytes / Math.pow(unit, exp), pre);
    }
}
