package com.diwayou.web.crawl.handler;

import com.diwayou.web.config.CrawlConfig;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.script.CrawlScript;
import com.diwayou.web.script.ScriptRegistry;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.URLCanonicalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ScriptCrawlPageHandler implements PageHandler {

    private static final Logger log = Logger.getLogger(ScriptCrawlPageHandler.class.getName());

    private CrawlConfig crawlConfig;

    private ScriptEngine scriptEngine;

    public ScriptCrawlPageHandler(CrawlConfig crawlConfig) {
        this.crawlConfig = crawlConfig;

        this.scriptEngine = new ScriptEngineManager().getEngineByName("groovy");
    }

    @Override
    public void handle(Page page, Spider spider) {
        if (page.statusCode() != 200) {
            log.log(Level.WARNING, String.format("拉取%s不是200状态", page.getRequest().getUrl()));
            return;
        }

        log.info("处理url=" + page.getRequest().getUrl());

        CrawlScript crawlScript = ScriptRegistry.one().match(page);
        if (crawlScript == null) {
            log.warning("没有配置处理脚本url=" + page.getRequest().getUrl());
            return;
        }

        if (!PageUtil.isHtml(page)) {
            log.log(Level.INFO, "不是网页url=" + page.getRequest().getUrl());
            return;
        }

        String content = page.bodyAsString();
        Document document = Jsoup.parse(content);

        submit(document.select("a[href]").stream()
                .map(e -> e.attr("href")), page, spider);

        if (page.getRequest().getFetcherType().equals(FetcherType.JAVA_HTTP)) {
            submit(document.select("img").stream()
                    .map(e -> e.attr("src")), page, spider);
        } else if (page.getRequest().getFetcherType().equals(FetcherType.FX_WEBVIEW)) {
            Set<String> resourceUrls = ((HtmlDocumentPage) page).getResourceUrls();

            for (String resourceUrl : resourceUrls) {
                spider.submitRequest(newRequest(resourceUrl, page.getRequest()));
            }
        }
    }

    private void submit(Stream<String> urlStream, Page page, Spider spider) {
        urlStream
                .map(u -> {
                    try {
                        return URLCanonicalizer.getCanonicalURL(u, page.getRequest().getUrl());
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(u -> !crawlConfig.getUrlStore().contain(u))
                .peek(u -> crawlConfig.getUrlStore().add(u))
                .map(u -> newRequest(u, page.getRequest()))
                .filter(r -> r.getDepth() < crawlConfig.getMaxDepth())
                .forEach(spider::submitRequest);
    }

    private Request newRequest(String newUrl, Request old) {
        return new Request(newUrl)
                .setParentUrl(old.getUrl())
                .setDepth(old.getDepth() + 1);
    }
}
