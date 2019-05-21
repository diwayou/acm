package com.diwayou.web.crawl.handler;

import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.URLCanonicalizer;
import com.diwayou.web.url.UrlUtil;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class ScriptCrawlPageHandler implements PageHandler {

    private static final Logger log = AppLog.getCrawl();

    @Override
    public void handle(Page page, Spider spider) {
        if (page.statusCode() != 200) {
            log.log(Level.WARNING, String.format("拉取%s不是200状态", page.getRequest().getUrl()));
            return;
        }

        log.info("处理url=" + page.getRequest().getUrl());

        if (PageUtil.isHtml(page)) {
            spider.getLucenePageStore().store(page);

            String content = page.bodyAsString();
            Document document = Jsoup.parse(content);

            Set<String> urls = allUrls(document, page);

            submit(urls, spider, page);
        } else {
            spider.getLucenePageStore().store(page);
        }
    }

    private void submit(Set<String> scriptResult, Spider spider, Page page) {
        List<Request> requests = scriptResult.stream()
                .filter(Objects::nonNull)
                .filter(u -> !spider.getUrlStore().contain(u))
                .peek(u -> spider.getUrlStore().add(u))
                .map(u -> newRequest(u, page))
                .filter(Request::needCrawl)
                .collect(Collectors.toList());

        spider.submitRequest(requests);
    }

    private Request newRequest(String newUrl, Page page) {
        return new Request(newUrl)
                .setParentUrl(page.getRequest().getUrl())
                .setDepth(page.getRequest().getDepth() + 1)
                .setPriority(getPriority(newUrl));
    }

    private byte getPriority(String url) {
        if (UrlUtil.isImage(url)) {
            return Request.MAX_PRIORITY;
        } else if (UrlUtil.hasExtension(url)) {
            return Request.MIN_PRIORITY;
        }

        return Request.NORMAL_PRIORITY;
    }

    private Set<String> allUrls(Document document, Page page) {
        Set<String> urls = Sets.newHashSet();

        collectUrls(document.select("a, area, link, base"), "abs:href", page, urls);

        if (page.getRequest().getFetcherType().equals(FetcherType.JAVA_HTTP)) {
            collectUrls(document.select("img, iframe, frame, embed, script"), "abs:src", page, urls);
        } else if (page.getRequest().getFetcherType().equals(FetcherType.FX_WEBVIEW)) {
            Set<String> resourceUrls = ((HtmlDocumentPage) page).getResourceUrls();

            if (resourceUrls != null) {
                resourceUrls = removeBase64Image(resourceUrls);
                urls.addAll(resourceUrls);
            }
        }

        return urls;
    }

    private Set<String> removeBase64Image(Set<String> resourceUrls) {
        return resourceUrls.stream()
                .filter(u -> !u.startsWith("data:image"))
                .collect(Collectors.toUnmodifiableSet());
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
