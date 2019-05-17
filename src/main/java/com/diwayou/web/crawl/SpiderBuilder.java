package com.diwayou.web.crawl;

import com.diwayou.web.crawl.handler.ScriptCrawlPageHandler;
import com.diwayou.web.store.LevelDbUrlStore;
import com.diwayou.web.store.UrlStore;

import java.nio.file.Path;

public class SpiderBuilder {

    private int crawlThreadNum = 2;

    private PageHandler pageHandler = new ScriptCrawlPageHandler();

    private Path storePath;

    private UrlStore urlStore;

    private Path scriptsPath;

    private SpiderBuilder() {
    }

    public static SpiderBuilder newBuilder(Path storePath) {
        UrlStore urlStore = new LevelDbUrlStore(storePath);
        return new SpiderBuilder()
                .setStorePath(storePath)
                .setUrlStore(urlStore)
                .setScriptsPath(storePath.resolve("scripts"));
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

    public UrlStore getUrlStore() {
        return urlStore;
    }

    public SpiderBuilder setUrlStore(UrlStore urlStore) {
        this.urlStore = urlStore;
        return this;
    }

    public Path getScriptsPath() {
        return scriptsPath;
    }

    public SpiderBuilder setScriptsPath(Path scriptsPath) {
        this.scriptsPath = scriptsPath;
        return this;
    }
}
