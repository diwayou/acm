package com.diwayou.web.crawl.handler;

import com.diwayou.web.config.CrawlConfig;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.store.PageStoreContext;
import com.diwayou.web.support.PageUtil;

import java.util.logging.Logger;

public class ScriptHelper {

    private static final Logger log = Logger.getLogger(ScriptHelper.class.getName());

    private Spider spider;

    private Page page;

    private CrawlConfig crawlConfig;

    public ScriptHelper(Spider spider, Page page, CrawlConfig crawlConfig) {
        this.spider = spider;
        this.page = page;
        this.crawlConfig = crawlConfig;
    }

    public void submitRequest(Request request) {
        if (request == null) {
            return;
        }

        if (request.getUrl() == null) {
            return;
        }

        if (crawlConfig.getUrlStore().contain(request.getUrl())) {
            return;
        }

        crawlConfig.getUrlStore().add(request.getUrl());

        if (request.getParentUrl() == null) {
            request.setParentUrl(page.getRequest().getUrl());
        }

        request.setDepth(page.getRequest().getDepth() + 1);

        if (request.getDepth() > crawlConfig.getMaxDepth()) {
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

    public void store(PageStoreContext context) {
        if (crawlConfig.getPageStore() == null) {
            log.warning("没有配置pageStore!");
        }

        crawlConfig.getPageStore().store(page, context);
    }
}
