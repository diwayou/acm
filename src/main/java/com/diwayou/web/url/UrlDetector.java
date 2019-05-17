package com.diwayou.web.url;

import com.diwayou.web.domain.Request;

public class UrlDetector {

    private static Request newRequest(String newUrl, Request old) {
        return new Request(newUrl)
                .setParentUrl(old.getUrl())
                .setDepth(old.getDepth() + 1);
    }
}
