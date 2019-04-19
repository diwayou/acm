package com.diwayou.web.support;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.google.common.net.HttpHeaders;

public class PageUtil {

    public static boolean isHtml(Page page) {
        if (FetcherType.FX_WEBVIEW.equals(page.getRequest().getFetcherType())) {
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

    public static String getImageExt(String contentType) {
        if (!isImage(contentType)) {
            return null;
        }

        int idx = contentType.indexOf("/");

        if (idx > 0) {
            return contentType.substring(idx + 1);
        }

        return null;
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
}
