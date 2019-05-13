package com.diwayou.web.crawl;

import com.alibaba.fastjson.JSON;
import com.diwayou.web.domain.HtmlDocumentPage;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.scheduler.FlowScheduler;
import com.diwayou.web.scheduler.Scheduler;
import com.diwayou.web.store.LevelDbQuery;
import com.diwayou.web.store.LevelDbStore;
import com.diwayou.web.store.StoreQuery;
import com.diwayou.web.support.Sleeper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Spider implements Closeable {

    private static final Logger log = Logger.getLogger(Spider.class.getName());

    private SpiderBuilder builder;

    private Scheduler<Request> requestScheduler;

    private Scheduler<Page> pageScheduler;

    private FetcherFactory fetcherFactory;

    private PageHandler pageHandler;

    private LevelDbStore requestStore;

    private AtomicInteger state = new AtomicInteger();

    private static final int INIT = 0;
    private static final int RUNNING = 1;
    private static final int STOPPED = 2;

    private ScheduledExecutorService requestScheduledService;

    private static final byte[] WAITING_NAMESPACE = new byte[]{0};
    private static final byte[] CRAWLING_NAMESPACE = new byte[]{9};

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
        if (builder.getRequestStorePath() != null) {
            try {
                this.requestStore = new LevelDbStore(builder.getRequestStorePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            requestScheduledService = Executors.newSingleThreadScheduledExecutor();
            scheduleRequest();
        }

        start();
    }

    private void scheduleRequest() {
        requestScheduledService.scheduleWithFixedDelay(() -> {
            try {
                LevelDbQuery levelDbQuery = new LevelDbQuery().setOffset(genKey(WAITING_NAMESPACE, "")).setNamespace(WAITING_NAMESPACE);
                StoreQuery<LevelDbQuery> query = StoreQuery.create(levelDbQuery);
                List<Map.Entry<byte[], byte[]>> pageResult = requestStore.query(query);
                do {
                    List<Request> requests = pageResult.stream()
                            .map(Map.Entry::getValue)
                            .map(v -> new String(v, StandardCharsets.UTF_8))
                            .map(v -> JSON.parseObject(v, Request.class))
                            .collect(Collectors.toList());
                    if (requests.isEmpty()) {
                        break;
                    }

                    List<Request> pushSuccessList = Lists.newArrayListWithCapacity(requests.size());
                    try {
                        for (Request request : requests) {
                            boolean result = requestScheduler.push(request);
                            if (result) {
                                pushSuccessList.add(request);
                            } else {
                                log.info("调度太快，暂停下...");
                                TimeUnit.SECONDS.sleep(1);
                            }
                        }
                    } catch (Exception e) {
                        log.log(Level.WARNING, "", e);
                        break;
                    }

                    requestStore.write(wb -> pushSuccessList.forEach(r -> {
                        wb.delete(genKey(WAITING_NAMESPACE, r.getUrl()));
                        wb.put(genKey(CRAWLING_NAMESPACE, r.getUrl()), genValue(r));
                    }));

                    if (requests.size() < query.getPageSize()) {
                        break;
                    }

                    levelDbQuery.setOffset(genKey(WAITING_NAMESPACE, requests.get(requests.size() - 1).getUrl()));
                    pageResult = requestStore.query(query);
                } while (true);
            } catch (Exception e) {
                log.log(Level.WARNING, "", e);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        state.set(STOPPED);

        try {
            requestScheduler.close();
            pageScheduler.close();
            if (requestStore != null) {
                requestStore.close();
                requestScheduledService.shutdownNow();
            }
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
                // TODO 先简单粗暴的处理
                submitRequest(page.getRequest());

                try {
                    Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(1));
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, "", e);
                }
            } else if (requestStore != null) {
                requestStore.write(wb -> wb.delete(genKey(CRAWLING_NAMESPACE, request.getUrl())));
            }
        } catch (Exception e) {
            log.warning("处理request失败url=" + request.getUrl());
        }
    }

    private byte[] genKey(byte[] namespace, String url) {
        byte[] bUrl = url.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(namespace.length + bUrl.length);
        buffer.put(namespace);
        buffer.put(bUrl);

        return buffer.array();
    }

    private byte[] genValue(Request request) {
        return JSON.toJSONBytes(request);
    }

    private void processPage(Page page) {
        try {
            pageHandler.handle(page, this);
        } catch (Exception e) {
            log.log(Level.SEVERE, "处理page失败url=" + page.getRequest().getUrl(), e);
        }
    }

    public void submitRequest(Request request) throws IOException {
        submitRequest(Collections.singletonList(request));
    }

    public void submitRequest(List<Request> requests) throws IOException {
        if (state.get() != RUNNING) {
            throw new IllegalStateException("当前spider不在运行中state=" + state.get());
        }

        if (requestStore == null) {
            requests.forEach(requestScheduler::push);
            return;
        }

        requestStore.write(wb -> {
            for (Request request : requests) {
                wb.put(genKey(WAITING_NAMESPACE, request.getUrl()), genValue(request));
            }
        });
    }

    public boolean submitPage(HtmlDocumentPage page) {
        if (state.get() != RUNNING) {
            throw new IllegalStateException("当前spider不在运行中state=" + state.get());
        }

        return pageScheduler.push(page);
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
