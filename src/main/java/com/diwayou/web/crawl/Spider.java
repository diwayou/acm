package com.diwayou.web.crawl;

import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Request;
import com.diwayou.web.scheduler.RequestScheduler;
import com.diwayou.web.scheduler.Scheduler;
import com.diwayou.web.script.ScriptRegistry;
import com.diwayou.web.store.FilePageStore;
import com.diwayou.web.store.LucenePageStore;
import com.diwayou.web.store.UrlStore;
import com.google.common.base.Preconditions;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Spider implements Closeable {

    private static final Logger log = Logger.getLogger(Spider.class.getName());

    private SpiderBuilder builder;

    private Scheduler<Request> requestScheduler;

    private AtomicInteger state = new AtomicInteger();

    private static final int INIT = 0;
    private static final int RUNNING = 1;
    private static final int STOPPED = 2;

    private FilePageStore filePageStore;

    private LucenePageStore lucenePageStore;

    private PageHandler pageHandler;

    private UrlStore urlStore;

    Spider(SpiderBuilder builder) {
        Preconditions.checkNotNull(builder.getPageHandler());
        Preconditions.checkNotNull(builder.getStorePath());
        Preconditions.checkNotNull(builder.getUrlStore());

        this.builder = builder;
        this.pageHandler = builder.getPageHandler();
        this.urlStore = builder.getUrlStore();

        try {
            ScriptRegistry.one().load(builder.getScriptsPath().toFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.filePageStore = new FilePageStore(builder.getStorePath().resolve("doc").toFile());
        try {
            this.lucenePageStore = new LucenePageStore(filePageStore, builder.getStorePath().resolve("index"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.requestScheduler = new RequestScheduler(this, builder.getCrawlThreadNum());
        start();
    }

    @Override
    public void close() {
        state.set(STOPPED);

        try {
            requestScheduler.close();
            lucenePageStore.close();
        } catch (IOException ignore) {
        }
    }

    public void submitRequest(Request request) {
        requestScheduler.push(request);
    }

    public void submitRequest(List<Request> requests) {
        if (!isRunning()) {
            throw new IllegalStateException("当前spider不在运行中state=" + state.get());
        }

        requestScheduler.push(requests);
    }

    public void submitPage(HtmlDocumentPage page) {
        if (!isRunning()) {
            throw new IllegalStateException("当前spider不在运行中state=" + state.get());
        }

        getPageHandler().handle(page, this);
    }

    private void start() {
        if (!state.compareAndSet(INIT, RUNNING)) {
            throw new IllegalStateException("运行状态不正确!");
        }
    }

    public void waitUntilStop() throws InterruptedException {
        while (state.get() == RUNNING) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public boolean isRunning() {
        return state.get() == RUNNING;
    }

    public Path getStorePath() {
        return builder.getStorePath();
    }

    public PageHandler getPageHandler() {
        return pageHandler;
    }

    public int getMaxDepth() {
        return builder.getMaxDepth();
    }

    public FilePageStore getFilePageStore() {
        return filePageStore;
    }

    public LucenePageStore getLucenePageStore() {
        return lucenePageStore;
    }

    public UrlStore getUrlStore() {
        return urlStore;
    }
}
