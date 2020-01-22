package com.diwayou.spring.manager;

import com.diwayou.spring.domain.CnbetaNewsVo;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.FetcherFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class CnbetaNewsManager {

    @Autowired
    private SpiderManager spiderManager;

    private Deque<CnbetaNewsVo> resultQueue = new ConcurrentLinkedDeque<>();

    private AtomicLong curId = new AtomicLong();

    @PostConstruct
    public void init() {
        reload(30);
    }

    private void refresh() {
        Request request = new Request("https://www.cnbeta.com")
                .setFetcherType(FetcherType.JAVA_HTTP)
                .setTimeout(2);
        Page page = FetcherFactory.one().getJavaHttpFetcher().fetch(request);
        if (page.statusCode() != 200) {
            log.warn("拉取{}不是200状态", page.getRequest().getUrl());
            throw new RuntimeException("Init fail...");
        }

        String content = page.bodyAsString();
        Document document = Jsoup.parse(content);

        String maxId = document.select(".items-area .item").next().next().attr("id");

        curId.set(Long.parseLong(maxId.substring(maxId.lastIndexOf('_') + 1)));
    }

    public List<CnbetaNewsVo> reload(int size) {
        refresh();

        return nextPage(size);
    }

    @Trace
    public List<CnbetaNewsVo> nextPage(int size) {
        log.info("TraceId={}", TraceContext.traceId());
        ActiveSpan.tag("size", String.valueOf(size));

        List<CnbetaNewsVo> result = new ArrayList<>(size);
        if (!resultQueue.isEmpty()) {
            int i = 0;
            while (i++ < size && !resultQueue.isEmpty()) {
                result.add(resultQueue.removeFirst());
            }
        }

        if (resultQueue.size() < size) {
            load(size * 5);
        }

        return result;
    }

    private void load(int size) {
        for (int i = 0; i < size; i++) {
            Request request = new Request(String.format("https://www.cnbeta.com/articles/tech/%d.htm", curId.get()))
                    .setFetcherType(FetcherType.JAVA_HTTP)
                    .setTimeout(2);
            spiderManager.get(request, page -> {
                String content = page.bodyAsString();
                Document document = Jsoup.parse(content);

                String title = document.title();
                String meta = document.select(".meta").text();
                String body = document.select("#artibody").text();

                resultQueue.addLast(new CnbetaNewsVo(title, meta, body, request.getUrl()));
            });

            curId.addAndGet(-2);
        }
    }
}
