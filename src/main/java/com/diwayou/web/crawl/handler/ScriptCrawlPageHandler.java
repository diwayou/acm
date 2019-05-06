package com.diwayou.web.crawl.handler;

import com.diwayou.web.config.CrawlConfig;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.script.CrawlScript;
import com.diwayou.web.script.ScriptRegistry;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.URLCanonicalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.script.Bindings;
import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScriptCrawlPageHandler implements PageHandler {

    private static final Logger log = Logger.getLogger(ScriptCrawlPageHandler.class.getName());

    private CrawlConfig crawlConfig;

    public ScriptCrawlPageHandler(CrawlConfig crawlConfig) {
        this.crawlConfig = crawlConfig;
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

        Bindings bindings = ScriptRegistry.one().createBindings();
        bindings.put("page", page);
        bindings.put("util", this);
        bindings.put("spider", spider);

        if (!PageUtil.isHtml(page)) {
            log.log(Level.INFO, "不是网页url=" + page.getRequest().getUrl());
            executeScript(crawlScript, bindings);
            return;
        }

        String content = page.bodyAsString();
        Document document = Jsoup.parse(content);
        bindings.put("doc", document);

        executeScript(crawlScript, bindings);
    }

    private void executeScript(CrawlScript crawlScript, Bindings bindings) {
        try {
            crawlScript.getCompiledScript().eval(bindings);
        } catch (ScriptException e) {
            log.log(Level.WARNING, "", e);
        }
    }

    private void submit(Elements elements, String attr, Page page, Spider spider) {
        elements.stream()
                .map(e -> e.attr(attr))
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

    public CrawlConfig getCrawlConfig() {
        return crawlConfig;
    }
}
