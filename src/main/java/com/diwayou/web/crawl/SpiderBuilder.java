package com.diwayou.web.crawl;

import com.diwayou.web.fetcher.FetcherFactory;

import java.io.File;
import java.util.concurrent.Executor;

public class SpiderBuilder {

    private Executor requestExecutor;

    private Executor pageExecutor;

    private PageHandler pageHandler;

    private FetcherFactory fetcherFactory;

    private File requestStorePath;

    private SpiderBuilder() {
    }

    public static SpiderBuilder newBuilder(FetcherFactory fetcherFactory, PageHandler pageHandler) {
        return new SpiderBuilder()
                .setFetcherFactory(fetcherFactory)
                .setPageHandler(pageHandler);
    }

    public Spider build() {
        return new Spider(this);
    }

    public Executor getRequestExecutor() {
        return requestExecutor;
    }

    public SpiderBuilder setRequestExecutor(Executor requestExecutor) {
        this.requestExecutor = requestExecutor;
        return this;
    }

    public Executor getPageExecutor() {
        return pageExecutor;
    }

    public SpiderBuilder setPageExecutor(Executor pageExecutor) {
        this.pageExecutor = pageExecutor;
        return this;
    }

    public PageHandler getPageHandler() {
        return pageHandler;
    }

    public SpiderBuilder setPageHandler(PageHandler pageHandler) {
        this.pageHandler = pageHandler;
        return this;
    }

    public FetcherFactory getFetcherFactory() {
        return fetcherFactory;
    }

    public SpiderBuilder setFetcherFactory(FetcherFactory fetcherFactory) {
        this.fetcherFactory = fetcherFactory;
        return this;
    }

    public File getRequestStorePath() {
        return requestStorePath;
    }

    public SpiderBuilder setRequestStorePath(File requestStorePath) {
        this.requestStorePath = requestStorePath;
        return this;
    }
}
