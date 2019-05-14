package com.diwayou.web.config;

import com.diwayou.web.store.FilePageStore;
import com.diwayou.web.store.MemoryUrlStore;
import com.diwayou.web.store.PageStore;
import com.diwayou.web.store.UrlStore;

public class CrawlConfig {

    private UrlStore urlStore = new MemoryUrlStore();

    private PageStore pageStore = new FilePageStore(null);

    public UrlStore getUrlStore() {
        return urlStore;
    }

    public CrawlConfig setUrlStore(UrlStore urlStore) {
        this.urlStore = urlStore;
        return this;
    }

    public PageStore getPageStore() {
        return pageStore;
    }

    public CrawlConfig setPageStore(PageStore pageStore) {
        this.pageStore = pageStore;
        return this;
    }
}
