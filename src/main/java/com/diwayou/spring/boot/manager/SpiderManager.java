package com.diwayou.spring.boot.manager;

import com.diwayou.web.crawl.PageHandler;
import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@Slf4j
@Component
public class SpiderManager {

    private Spider spider;

    private ConcurrentMap<Request, Consumer<Page>> processingRequests = new ConcurrentHashMap<>(100);

    @PostConstruct
    public void init() {
        spider = SpiderBuilder.newBuilder(Path.of("cnbeta"))
                .setCrawlThreadNum(3)
                .setPageHandler(new PageHandler() {
                    @Override
                    public void handle(Page page, Spider spider) {
                        if (page.statusCode() != 200) {
                            log.warn("拉取{}不是200状态", page.getRequest().getUrl());
                            return;
                        }

                        Consumer<Page> consumer = processingRequests.remove(page.getRequest());
                        if (consumer != null) {
                            consumer.accept(page);
                        }
                    }
                })
                .build();
    }

    @PreDestroy
    public void destroy() {
        spider.close();
    }

    public void get(Request request, Consumer<Page> consumer) {
        Preconditions.checkNotNull(consumer, "Callback can't be null!");

        processingRequests.put(request, consumer);

        spider.submitRequest(request);
    }
}
