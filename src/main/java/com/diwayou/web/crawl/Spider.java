package com.diwayou.web.crawl;

import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.scheduler.FlowScheduler;
import com.diwayou.web.scheduler.Scheduler;
import com.diwayou.web.support.Sleeper;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Spider implements Closeable {

    private static final Logger log = Logger.getLogger(Spider.class.getName());

    private SpiderBuilder builder;

    private Scheduler<Request> requestScheduler;

    private Scheduler<Page> pageScheduler;

    private FetcherFactory fetcherFactory;

    private PageHandler pageHandler;

    private AtomicInteger state = new AtomicInteger();

    private static final int INIT = 0;
    private static final int RUNNING = 1;
    private static final int STOPPED = 2;

    Spider(SpiderBuilder builder) {
        this.builder = builder;
        init();
    }

    private void init() {
        Preconditions.checkNotNull(builder.getPageHandler());
        Preconditions.checkNotNull(builder.getFetcherFactory());

        pageHandler = builder.getPageHandler();

        if (builder.getRequestExecutor() == null) {
            this.requestScheduler = new FlowScheduler<>("requestScheduler", MoreExecutors.directExecutor(), this::processRequest);
        } else {
            this.requestScheduler = new FlowScheduler<>("requestScheduler", builder.getRequestExecutor(), this::processRequest);
        }

        if (builder.getPageExecutor() == null) {
            this.pageScheduler = new FlowScheduler<>("pageScheduler", MoreExecutors.directExecutor(), this::processPage);
        } else {
            this.pageScheduler = new FlowScheduler<>("pageScheduler", builder.getPageExecutor(), this::processPage);
        }

        this.fetcherFactory = builder.getFetcherFactory();

        start();
    }

    @Override
    public void close() {
        state.set(STOPPED);

        try {
            requestScheduler.close();
            pageScheduler.close();
        } catch (IOException ignore) {
        }
    }

    private void processRequest(Request request) {
        try {
            if (state.get() != RUNNING) {
                log.warning("已经停止运行，未处理url=" + request.getUrl());
                return;
            }

            Fetcher fetcher = fetcherFactory.getFetcher(request.getFetcherType());
            Page page = fetcher.fetch(request);

            if (!pageScheduler.push(page)) {
                try {
                    Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(1));
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, "", e);
                }
            }
        } catch (Exception e) {
            log.warning("处理request失败url=" + request.getUrl());
        }
    }

    private void processPage(Page page) {
        try {
            pageHandler.handle(page, this);
        } catch (Exception e) {
            log.log(Level.SEVERE, "处理page失败url=" + page.getRequest().getUrl());
        }
    }

    public boolean submitRequest(Request request) {
        if (state.get() != RUNNING) {
            throw new IllegalStateException("当前spider不在运行中state=" + state.get());
        }

        return requestScheduler.push(request);
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
}
