package com.diwayou.web.support;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.StreamPage;

import java.net.http.HttpHeaders;

public class PageUtil {

    public static boolean isHtml(Page page) {
        if (FetcherType.FX_WEBVIEW.equals(page.getRequest().getFetcherType())) {
            return true;
        }

        StreamPage streamPage = (StreamPage) page;
        HttpHeaders headers = streamPage.getHttpHeaders();
        String contentType = headers.firstValue("Content-Type").orElse(null);
        if (contentType == null) {
            return false;
        }

        return contentType.contains("text") && contentType.contains("html");
    }
}
