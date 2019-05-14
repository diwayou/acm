package com.diwayou.web.fetcher;

import com.diwayou.web.domain.FetcherType;

import java.io.Closeable;

public class FetcherFactory implements Closeable {

    private static final FetcherFactory instance = new FetcherFactory();

    private JavaHttpFetcher javaHttpFetcher;

    private FxWebviewFetcher fxWebviewFetcher;

    public static FetcherFactory one() {
        return instance;
    }

    private FetcherFactory() {
        this(16);
    }

    private FetcherFactory(int fxPoolCapacity) {
        this.javaHttpFetcher = new JavaHttpFetcher();
        this.fxWebviewFetcher = new FxWebviewFetcher(fxPoolCapacity);

        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
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

    public JavaHttpFetcher getJavaHttpFetcher() {
        return javaHttpFetcher;
    }

    public FxWebviewFetcher getFxWebviewFetcher() {
        return fxWebviewFetcher;
    }

    @Override
    public void close() {
        this.javaHttpFetcher.close();
        this.fxWebviewFetcher.close();
    }
}
