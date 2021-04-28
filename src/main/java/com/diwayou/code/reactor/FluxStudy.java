package com.diwayou.code.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author gaopeng 2021/4/27
 */
public class FluxStudy {

    public static void main(String[] args) throws InterruptedException {
        Flux.merge(Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(5),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .subscribe((t) -> System.out.println("Flux.merge : " + t + " : " + Thread.currentThread().getName()));

        Thread.sleep(2000l);
    }
}
