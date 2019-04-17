package com.diwayou.web.fetcher;

import com.diwayou.web.domain.FetcherType;

public class FetcherFactory {

    public static Fetcher getFetcher(FetcherType type) {
        switch (type) {
            case JAVA_HTTP:
                return new JavaHttpFetcher();
            case FX_WEBVIEW:
                return new FxWebviewFetcher();
            default:
                throw new IllegalArgumentException("FetcherType不正确");
        }
    }
}
