package com.diwayou.code.reactor;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author gaopeng 2021/4/27
 */
public class FluxStudy {

    public static void main(String[] args) throws InterruptedException {
        Flux.range(1, 30)
                .groupBy(i -> i % 3)
                .flatMap(gf -> gf.collectList().map(l -> Tuples.of(gf.key(), l)))
                .subscribe(System.out::println);

        Thread.sleep(TimeUnit.HOURS.toMillis(1));
    }

    private static Stream<String> getMovie(){
        System.out.println("Got the movie streaming request");
        return Stream.of(
                "scene 1",
                "scene 2",
                "scene 3",
                "scene 4",
                "scene 5"
        );
    }
}
