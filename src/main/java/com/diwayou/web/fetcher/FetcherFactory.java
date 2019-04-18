package com.diwayou.web.fetcher;

import com.diwayou.web.domain.FetcherType;

public class FetcherFactory implements AutoCloseable {

    private JavaHttpFetcher javaHttpFetcher;

    private FxWebviewFetcher fxWebviewFetcher;

    public FetcherFactory() {
        this.javaHttpFetcher = new JavaHttpFetcher();
        this.fxWebviewFetcher = new FxWebviewFetcher();
    }

    public Fetcher getFetcher(FetcherType type) {
        switch (type) {
            case JAVA_HTTP:
                return javaHttpFetcher;
            case FX_WEBVIEW:
                return fxWebviewFetcher;
            default:
                throw new IllegalArgumentException("FetcherType不正确");
        }
    }

    @Override
    public void close() throws Exception {
        this.javaHttpFetcher.close();
        this.fxWebviewFetcher.close();
    }
}
