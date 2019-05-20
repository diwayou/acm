package com.diwayou.web.ui.spider;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Request;
import com.diwayou.web.log.AppLog;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpiderSingleton {

    private static final Logger log = AppLog.getBrowser();

    private Spider spider;

    private static SpiderSingleton instance;

    private SpiderSingleton() {
    }

    public static synchronized SpiderSingleton one() {
        if (instance == null) {
            instance = new SpiderSingleton();
            try {
                instance.init();
            } catch (Exception e) {
                log.log(Level.WARNING, "", e);
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public void submitRequest(Request request) {
        spider.submitRequest(request);
    }

    public void submitPage(HtmlDocumentPage page) {
        spider.submitPage(page);
    }

    private void init() {
        Path storePath = new File(".").toPath();
        Path scriptPath = storePath.resolve("scripts");

        spider = SpiderBuilder.newBuilder(storePath)
                .setCrawlThreadNum(Runtime.getRuntime().availableProcessors())
                .setScriptsPath(scriptPath)
                .build();
    }

    public Path getIndexPath() {
        return spider.getLucenePageStore().getIndexPath();
    }
}
