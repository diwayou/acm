package com.diwayou.spring.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gaopeng 2021/4/27
 */
@Slf4j
public class ConcurrentDownload {

    public static void main(String[] args) {
        WebClient client = WebClient.builder()
                .baseUrl("https://www.baidu.com")
                .build();

        List<Mono<String>> result = Stream.of(1, 2, 3, 4, 5)
                .map(i -> client.get().retrieve()
                        .bodyToMono(String.class)
                        .map(s -> {
                            log.info("结果: {}", s);
                            return s;
                        }))
                .collect(Collectors.toList());

        Mono.when(result).block();
    }
}
