package com.diwayou.web.crawl;

import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Request;
import com.diwayou.web.scheduler.RequestScheduler;
import com.diwayou.web.scheduler.Scheduler;
import com.diwayou.web.store.*;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.RocksDBException;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Spider implements Closeable {

    private Scheduler<Request> requestScheduler;

    private AtomicInteger state = new AtomicInteger();

    private static final int INIT = 0;
    private static final int RUNNING = 1;
    private static final int STOPPED = 2;

    private Path storePath;

    private LevelDbStore levelDbStore;

    private FilePageStore filePageStore;

    private LucenePageStore lucenePageStore;

    private PageHandler pageHandler;

    private UrlStore urlStore;

    private int connTimeout;

    private int timeout;

    Spider(SpiderBuilder builder) {
        Preconditions.checkNotNull(builder.getPageHandler());
        Preconditions.checkNotNull(builder.getStorePath());
        Preconditions.checkNotNull(builder.getUrlStoreType());

        this.storePath = builder.getStorePath();
        this.pageHandler = builder.getPageHandler();

        try {
            this.levelDbStore = new LevelDbStore(storePath.resolve("db").toFile());
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }

        if (builder.getUrlStoreType().equals(UrlStoreType.LevelDb)) {
            this.urlStore = new LevelDbUrlStore(levelDbStore);
        } else {
            this.urlStore = new MemoryUrlStore();
        }

        this.connTimeout = builder.getConnTimeout();
        this.timeout = builder.getTimeout();

        this.filePageStore = new FilePageStore(storePath.resolve("doc").toFile());
        try {
            this.lucenePageStore = new LucenePageStore(filePageStore, storePath.resolve("index"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.requestScheduler = new RequestScheduler(this, builder.getCrawlThreadNum());
        start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        state.set(STOPPED);

        try {
            requestScheduler.close();
            lucenePageStore.close();
            levelDbStore.close();
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
        log.info("提交page请求originalUrl={}, url={}", page.getRequest().getUrl(), page.getDocument().getURL());

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
        return storePath;
    }

    public PageHandler getPageHandler() {
        return pageHandler;
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

    public int getConnTimeout() {
        return connTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public LevelDbStore getLevelDbStore() {
        return levelDbStore;
    }
}
