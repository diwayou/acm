package com.diwayou.web.crawl;

import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.scheduler.FlowScheduler;
import com.diwayou.web.scheduler.Scheduler;
import com.diwayou.web.store.MemoryUrlStore;
import com.diwayou.web.store.UrlStore;
import com.diwayou.web.support.Sleeper;
import com.google.common.collect.Lists;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Spider implements AutoCloseable {

    private static final Logger log = Logger.getLogger(Spider.class.getName());

    private SpiderBuilder builder;

    private Scheduler<Request> requestScheduler;

    private Scheduler<Page> pageScheduler;

    private FetcherFactory fetcherFactory;

    private List<Request> seeds;

    private List<PageHandler> pageHandlers;

    private UrlStore urlStore;

    private int maxDepth;

    private AtomicInteger state = new AtomicInteger();

    private static final int INIT = 0;
    private static final int RUNNING = 1;
    private static final int STOPPED = 2;

    Spider(SpiderBuilder builder) {
        this.builder = builder;
        this.fetcherFactory = new FetcherFactory();
        this.seeds = builder.getSeeds();
        this.maxDepth = builder.getMaxDepth();
        init();
    }

    private void init() {
        if (seeds == null || seeds.isEmpty()) {
            throw new IllegalArgumentException("seeds不能为空!");
        }
        if (builder.getPageHandler() == null) {
            throw new IllegalArgumentException("pageHandler不能为空!");
        }

        if (builder.getUrlStore() == null) {
            urlStore = new MemoryUrlStore();
        }

        pageHandlers = Lists.newArrayList(new CrawlPageHandler(), builder.getPageHandler());

        if (builder.getRequestScheduler() == null) {
            this.requestScheduler = new FlowScheduler<>(ForkJoinPool.commonPool(), 100);
        } else {
            this.requestScheduler = builder.getRequestScheduler();
        }

        if (builder.getPageScheduler() == null) {
            this.pageScheduler = new FlowScheduler<>(ForkJoinPool.commonPool(), 100);
        } else {
            this.pageScheduler = builder.getPageScheduler();
        }

        this.requestScheduler.subscriber(new RequestSubscriber());
        this.pageScheduler.subscriber(new PageSubscriber());
    }

    @Override
    public void close() throws Exception {
        requestScheduler.close();
        pageScheduler.close();
        fetcherFactory.close();
    }

    private class RequestSubscriber implements Flow.Subscriber<Request> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;

            subscription.request(1);
        }

        @Override
        public void onNext(Request item) {
            processRequest(item);

            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            log.log(Level.WARNING, "处理request错误", throwable);
        }

        @Override
        public void onComplete() {
            log.log(Level.INFO, "处理request完成");
        }
    }

    private class PageSubscriber implements Flow.Subscriber<Page> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;

            subscription.request(1);
        }

        @Override
        public void onNext(Page item) {
            processPage(item);

            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            log.log(Level.WARNING, "处理page错误", throwable);
        }

        @Override
        public void onComplete() {
            log.log(Level.INFO, "处理page完成");
        }
    }

    private void processRequest(Request request) {
        Fetcher fetcher = fetcherFactory.getFetcher(request.getFetcherType());
        Page page = fetcher.fetch(request);

        if (!pageScheduler.push(page)) {
            try {
                Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                log.log(Level.SEVERE, "", e);
            }
        }
    }

    private void processPage(Page page) {
        for (PageHandler pageHandler : pageHandlers) {
            try {
                if (pageHandler.interest(page.getRequest())) {
                    pageHandler.handle(page, this);
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "处理page失败", e);
            }
        }
    }

    public void submitRequest(Request request) {
        if (state.get() != RUNNING) {
            throw new IllegalStateException("当前spider不在运行中state=" + state.get());
        }

        if (request.getDepth() > maxDepth) {
            log.log(Level.INFO, "超过最大深度depth=" + request.getDepth());
            return;
        }

        if (!urlStore.contain(request.getUrl())) {
            requestScheduler.push(request);
            urlStore.add(request.getUrl());
        }
    }

    public void start() {
        if (!state.compareAndSet(INIT, RUNNING)) {
            throw new IllegalStateException("运行状态不正确!");
        }

        for (Request request : seeds) {
            submitRequest(request);
        }
    }

    public void startAndWait() throws InterruptedException {
        start();

        while (state.get() == RUNNING) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void stop() throws Exception {
        state.set(STOPPED);
        close();
    }
}
