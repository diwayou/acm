package com.diwayou.web.fetcher;

import com.diwayou.web.domain.FetcherType;

import java.io.Closeable;

public class FetcherFactory implements Closeable {

    private JavaHttpFetcher javaHttpFetcher;

    private FxWebviewFetcher fxWebviewFetcher;

    public FetcherFactory() {
        this.javaHttpFetcher = new JavaHttpFetcher();
        this.fxWebviewFetcher = new FxWebviewFetcher();
    }

    public FetcherFactory(int fxPoolCapacity) {
        this.javaHttpFetcher = new JavaHttpFetcher();
        this.fxWebviewFetcher = new FxWebviewFetcher(fxPoolCapacity);
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
    public void close() {
        this.javaHttpFetcher.close();
        this.fxWebviewFetcher.close();
    }
}
