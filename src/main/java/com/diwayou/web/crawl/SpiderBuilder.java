package com.diwayou.web.crawl;

import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.scheduler.Scheduler;
import com.diwayou.web.store.UrlStore;

import java.util.List;

public class SpiderBuilder {

    private Scheduler<Request> requestScheduler;

    private Scheduler<Page> pageScheduler;

    private List<Request> seeds;

    private PageHandler pageHandler;

    private UrlStore urlStore;

    private int maxDepth = 3;

    private SpiderBuilder() {
    }

    public static SpiderBuilder newBuilder(List<Request> seeds, PageHandler pageHandler) {
        return new SpiderBuilder()
                .setSeeds(seeds)
                .setPageHandler(pageHandler);
    }

    public Spider build() {
        return new Spider(this);
    }

    public Scheduler<Request> getRequestScheduler() {
        return requestScheduler;
    }

    public SpiderBuilder setRequestScheduler(Scheduler<Request> requestScheduler) {
        this.requestScheduler = requestScheduler;
        return this;
    }

    public Scheduler<Page> getPageScheduler() {
        return pageScheduler;
    }

    public SpiderBuilder setPageScheduler(Scheduler<Page> pageScheduler) {
        this.pageScheduler = pageScheduler;
        return this;
    }

    public List<Request> getSeeds() {
        return seeds;
    }

    public SpiderBuilder setSeeds(List<Request> seeds) {
        this.seeds = seeds;
        return this;
    }

    public PageHandler getPageHandler() {
        return pageHandler;
    }

    public SpiderBuilder setPageHandler(PageHandler pageHandler) {
        this.pageHandler = pageHandler;
        return this;
    }

    public UrlStore getUrlStore() {
        return urlStore;
    }

    public SpiderBuilder setUrlStore(UrlStore urlStore) {
        this.urlStore = urlStore;
        return this;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public SpiderBuilder setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
        return this;
    }
}
