package com.diwayou.web.crawl.handler;

import com.diwayou.web.config.CrawlConfig;
import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.support.FilenameUtil;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.URLCanonicalizer;
import com.hankcs.hanlp.HanLP;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CrawlPageHandler implements PageHandler {

    private static final Logger log = Logger.getLogger(CrawlPageHandler.class.getName());

    private CrawlConfig crawlConfig;

    public CrawlPageHandler(CrawlConfig crawlConfig) {
        this.crawlConfig = crawlConfig;
    }

    @Override
    public void handle(Page page, Spider spider) {
        if (page.statusCode() != 200) {
            log.log(Level.WARNING, String.format("拉取%s不是200状态", page.getRequest().getUrl()));
            return;
        }

        if (!PageUtil.isHtml(page)) {
            log.log(Level.INFO, "不是网页url=" + page.getRequest().getUrl());
            storeImage(page);
            return;
        }

        log.info("处理网页url=" + page.getRequest().getUrl());

        String content = page.bodyAsString();
        Document document = Jsoup.parse(content);

        String cleanContent = document.text();
        log.info("网页摘要: " + HanLP.extractSummary(cleanContent, 2));

        submit(document.select("a[href]").stream()
                .map(e -> e.attr("href")), page, spider);

        if (page.getRequest().getFetcherType().equals(FetcherType.JAVA_HTTP)) {
            submit(document.select("img").stream()
                    .map(e -> e.attr("src")), page, spider);
        } else if (page.getRequest().getFetcherType().equals(FetcherType.FX_WEBVIEW)) {
            Set<String> resourceUrls = ((HtmlDocumentPage) page).getResourceUrls();

            for (String resourceUrl : resourceUrls) {
                spider.submitRequest(newRequest(resourceUrl.replaceAll("[\\d]+x[\\d]+", "430x430"), page.getRequest()));
            }
        }
    }

    private void storeImage(Page page) {
        if (!page.getRequest().getFetcherType().equals(FetcherType.JAVA_HTTP)) {
            return;
        }

        if (PageUtil.getContentLength(page) < 1024 * 20) {
            return;
        }

        String ext = PageUtil.getImageExt(PageUtil.getContentType(page));
        if (ext == null) {
            return;
        }

        try {
            String dirPrefix = "D:/tmp/image";

            String name = FilenameUtil.genPathWithExt("", ext);
            String dir = FilenameUtils.getPath(name);
            if (!Files.exists(Path.of(dirPrefix, dir))) {
                Files.createDirectories(Path.of(dirPrefix, dir));
            }

            Files.copy(new ByteArrayInputStream(page.bodyAsByteArray()), Path.of(dirPrefix, name));
        } catch (IOException e) {
            log.warning("保存图片失败!");
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
