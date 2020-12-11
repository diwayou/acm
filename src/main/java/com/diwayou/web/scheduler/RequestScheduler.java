package com.diwayou.web.scheduler;

import com.diwayou.util.Json;
import com.diwayou.web.concurrent.FixedThreadPoolExecutor;
import com.diwayou.web.crawl.DbNamespace;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;
import com.diwayou.web.store.LevelDbQuery;
import com.diwayou.web.store.LevelDbStore;
import com.diwayou.web.store.StoreQuery;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.RocksDBException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
public class RequestScheduler implements Scheduler<Request> {

    private Spider spider;

    private ExecutorService threadPool;

    private ScheduledExecutorService requestScheduledService;

    private LevelDbStore requestStore;

    private static final int WAITING_NAMESPACE = DbNamespace.Waiting.getId();
    private static final int CRAWLING_NAMESPACE = DbNamespace.Crawling.getId();

    public RequestScheduler(Spider spider, int threadPoolSize) {
        this.spider = spider;
        this.threadPool = new FixedThreadPoolExecutor(threadPoolSize);
        this.requestStore = spider.getLevelDbStore();

        requestScheduledService = Executors.newSingleThreadScheduledExecutor();

        scheduleRequest();
    }

    private void scheduleRequest() {
        requestScheduledService.scheduleWithFixedDelay(() -> {
            try {
                LevelDbQuery levelDbQuery = new LevelDbQuery()
                        .setOffset(genKey(Request.empty))
                        .setNamespace(WAITING_NAMESPACE);
                StoreQuery<LevelDbQuery> query = StoreQuery.create(levelDbQuery);
                query.setPageSize(50);

                List<Map.Entry<byte[], byte[]>> pageResult = requestStore.query(query);
                do {
                    List<Request> requests = pageResult.stream()
                            .map(Map.Entry::getValue)
                            .map(v -> new String(v, StandardCharsets.UTF_8))
                            .map(v -> Json.nonNull().fromJson(v, Request.class))
                            .collect(Collectors.toList());
                    if (requests.isEmpty()) {
                        break;
                    }

                    requestStore.write(wb -> requests.forEach(r -> {
                        wb.delete(WAITING_NAMESPACE, genKey(r));
                        wb.put(CRAWLING_NAMESPACE, genKey(r), genValue(r));
                    }));

                    try {
                        for (Request request : requests) {
                            CompletableFuture.supplyAsync(() -> processRequest(request), threadPool)
                                    .thenApply(this::commitRequest)
                                    .whenComplete((r, e) -> {
                                        if (e != null) {
                                            log.warn("处理请求失败url=" + r.getUrl(), e);
                                        }
                                    });
                        }
                    } catch (Exception e) {
                        log.warn("", e);
                        break;
                    }

                    if (requests.size() < query.getPageSize()) {
                        break;
                    }

                    levelDbQuery.setOffset(genKey(requests.get(requests.size() - 1)));
                    pageResult = requestStore.query(query);
                } while (true);
            } catch (Exception e) {
                log.warn("", e);
            }
        }, 2, 2, TimeUnit.SECONDS);
    }

    private byte[] genValue(Request request) {
        return Json.nonNull().toBytes(request);
    }

    private byte[] genKey(Request request) {
        byte[] bUrl = request.getUrl().getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(1 + bUrl.length);
        buffer.put(request.getPriority());
        buffer.put(bUrl);

        return buffer.array();
    }

    private Request processRequest(Request request) {
        Fetcher fetcher = FetcherFactory.one().getFetcher(request.getFetcherType());
        Page page = fetcher.fetch(request);

        spider.getPageHandler().handle(page, spider);

        return request;
    }

    private Request commitRequest(Request request) {
        try {
            requestStore.write(wb -> wb.delete(CRAWLING_NAMESPACE, genKey(request)));
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }

        return request;
    }

    @Override
    public void push(List<Request> requests) {
        try {
            requestStore.write(wb -> {
                for (Request request : requests) {
                    wb.put(WAITING_NAMESPACE, genKey(request), genValue(request));
                }
            });
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        threadPool.shutdownNow();
        requestScheduledService.shutdownNow();
    }
}
