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
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.script.Bindings;
import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

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
        ScriptHelper helper = new ScriptHelper(spider, page, crawlConfig);
        bindings.put("helper", helper);

        if (PageUtil.isHtml(page)) {
            String content = page.bodyAsString();
            Document document = Jsoup.parse(content);
            bindings.put("doc", document);

            Set<String> urls = allUrls(document, page);
            bindings.put("urls", urls);
        } else {
            bindings.put("doc", null);
            bindings.put("urls", Collections.emptySet());
        }

        Object scriptResult = executeScript(crawlScript, bindings);
        if (scriptResult != null) {
            if (scriptResult instanceof Set) {
                submit((Set<String>) scriptResult, spider, page);
            }
        }
    }

    private void submit(Set<String> scriptResult, Spider spider, Page page) {
        scriptResult.stream()
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

    private Object executeScript(CrawlScript crawlScript, Bindings bindings) {
        try {
            return crawlScript.getCompiledScript().eval(bindings);
        } catch (ScriptException e) {
            log.log(Level.WARNING, "", e);
        }

        return null;
    }

    private Set<String> allUrls(Document document, Page page) {
        Set<String> urls = Sets.newHashSet();

        collectUrls(document.select("a, area, link, base"), "abs:href", page, urls);

        if (page.getRequest().getFetcherType().equals(FetcherType.JAVA_HTTP)) {
            collectUrls(document.select("img, iframe, frame, embed, script"), "abs:src", page, urls);
        } else if (page.getRequest().getFetcherType().equals(FetcherType.FX_WEBVIEW)) {
            Set<String> resourceUrls = ((HtmlDocumentPage) page).getResourceUrls();

            urls.addAll(resourceUrls);
        }

        return urls;
    }

    private Set<String> collectUrls(Elements elements, String attr, Page page, Set<String> urls) {
        Stream<String> url = elements.stream()
                .peek(e -> e.setBaseUri(page.getRequest().getUrl()))
                .map(e -> e.attr(attr))
                .filter(Objects::nonNull)
                .map(u -> getCanonicalURL(u, page))
                .filter(Objects::nonNull)
                .filter(u -> !u.isEmpty());

        if (urls == null) {
            return url.collect(toSet());
        } else {
            url.forEach(urls::add);
            return urls;
        }
    }

    private String getCanonicalURL(String url, Page page) {
        try {
            return URLCanonicalizer.getCanonicalURL(url, page.getRequest().getUrl());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
