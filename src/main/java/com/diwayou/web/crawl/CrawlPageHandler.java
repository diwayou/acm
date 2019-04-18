package com.diwayou.web.crawl;

import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.URLCanonicalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CrawlPageHandler implements PageHandler {

    private static final Logger log = Logger.getLogger(CrawlPageHandler.class.getName());

    @Override
    public boolean interest(Request request) {
        return true;
    }

    @Override
    public void handle(Page page, Spider spider) {
        if (page.statusCode() != 200) {
            log.log(Level.WARNING, String.format("拉取%s不是200状态", page.getRequest().getUrl()));
            return;
        }

        if (!PageUtil.isHtml(page)) {
            return;
        }

        Document document = Jsoup.parse(page.bodyAsString());

        submit(document.select("a[href]").stream()
                .map(e -> e.attr("href")), page, spider);

        submit(document.select("img").stream()
                .map(e -> e.attr("src")), page, spider);
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
                .map(u -> newRequest(u, page.getRequest()))
                .forEach(spider::submitRequest);
    }

    private Request newRequest(String newUrl, Request old) {
        return new Request(newUrl)
                .setParentUrl(old.getUrl())
                .setDepth(old.getDepth() + 1);
    }
}
