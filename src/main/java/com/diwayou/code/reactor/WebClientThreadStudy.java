package com.diwayou.code.reactor;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ReactorNetty;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gaopeng 2021/4/29
 */
@Slf4j
public class WebClientThreadStudy {

    public static void main(String[] args) {
        System.setProperty(ReactorNetty.IO_SELECT_COUNT, "" + Math.max(Runtime.getRuntime()
                .availableProcessors(), 4));
        System.setProperty(ReactorNetty.IO_WORKER_COUNT, "8");

        WebClient webClient = WebClient.builder()
                .baseUrl("https://www.baidu.com").build();

        AtomicInteger count = new AtomicInteger();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> re = Flux.merge(Flux.range(1, 1000)
                .subscribeOn(Schedulers.parallel())
                .map(i -> {
                    log.info("请求 {} ...", i);
                    return webClient.get()
                            .retrieve()
                            .bodyToMono(String.class);
                })
                .collectList()
                .map(Flux::merge))
                .publishOn(Schedulers.parallel())
                .map(s -> {
                    Document doc = Jsoup.parse(s);

                    log.info("标题 {}", count.incrementAndGet());

                    return s;
                })
                .collectList()
                .block();
        stopWatch.stop();

        log.info("用时 {} 秒", stopWatch.getTotalTimeSeconds());

        assert re != null;
        log.info("完成 {}", re.size());
    }
}
