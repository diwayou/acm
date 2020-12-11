package com.diwayou.web.crawl;

import com.diwayou.web.crawl.handler.ScriptCrawlPageHandler;
import com.diwayou.web.store.UrlStoreType;

import java.nio.file.Path;

public class SpiderBuilder {

    private int crawlThreadNum = 2;

    private PageHandler pageHandler = new ScriptCrawlPageHandler();

    private Path storePath;

    private UrlStoreType urlStoreType = UrlStoreType.LevelDb;

    private int connTimeout = 3;

    private int timeout = 5;

    private SpiderBuilder() {
    }

    public static SpiderBuilder newBuilder(Path storePath) {
        if (!storePath.toFile().exists() && !storePath.toFile().mkdirs()) {
            throw new RuntimeException("create directory fail storePath=" + storePath.toAbsolutePath().toString());
        }

        return new SpiderBuilder()
                .setStorePath(storePath);
    }

    public Spider build() {
        return new Spider(this);
    }

    public int getCrawlThreadNum() {
        return crawlThreadNum;
    }

    public SpiderBuilder setCrawlThreadNum(int crawlThreadNum) {
        this.crawlThreadNum = crawlThreadNum;
        return this;
    }

    public PageHandler getPageHandler() {
        return pageHandler;
    }

    public SpiderBuilder setPageHandler(PageHandler pageHandler) {
        this.pageHandler = pageHandler;
        return this;
    }

    public Path getStorePath() {
        return storePath;
    }

    public SpiderBuilder setStorePath(Path storePath) {
        this.storePath = storePath;
        return this;
    }

    public UrlStoreType getUrlStoreType() {
        return urlStoreType;
    }

    public SpiderBuilder setUrlStoreType(UrlStoreType urlStoreType) {
        this.urlStoreType = urlStoreType;
        return this;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public SpiderBuilder setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public SpiderBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
}
