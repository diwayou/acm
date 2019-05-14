package com.diwayou.web.crawl.handler;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.store.FilePageStore;
import com.diwayou.web.store.PageStoreContext;
import com.diwayou.web.support.PageUtil;

import java.nio.file.Path;
import java.util.logging.Logger;

public class ScriptHelper {

    private static final Logger log = Logger.getLogger(ScriptHelper.class.getName());

    private Spider spider;

    private Page page;

    public ScriptHelper(Spider spider, Page page) {
        this.spider = spider;
        this.page = page;
    }

    public void submitRequest(Request request) {
        if (request == null) {
            return;
        }

        if (request.getUrl() == null) {
            return;
        }

        if (spider.getUrlStore().contain(request.getUrl())) {
            return;
        }

        spider.getUrlStore().add(request.getUrl());

        if (request.getParentUrl() == null) {
            request.setParentUrl(page.getRequest().getUrl());
        }

        request.setDepth(page.getRequest().getDepth() + 1);

        if (request.getDepth() > spider.getMaxDepth()) {
            return;
        }

        spider.submitRequest(request);
    }

    public boolean isHtml() {
        return PageUtil.isHtml(page);
    }

    public boolean isImage() {
        return PageUtil.isImage(PageUtil.getContentType(page));
    }

    public String imageExt() {
        return PageUtil.getExt(page);
    }

    public String url() {
        return page.getRequest().getUrl();
    }

    public String parentUrl() {
        return page.getRequest().getParentUrl();
    }

    public long contentLength() {
        return PageUtil.getContentLength(page);
    }

    public void store(Path path) {
        spider.getFilePageStore().store(page, PageStoreContext.create().put(FilePageStore.DIR, path.toFile()));
    }

    public void storeWithIndex(Path path) {
        spider.getLucenePageStore().store(page, PageStoreContext.create().put(FilePageStore.DIR, path.toFile()));
    }
}
