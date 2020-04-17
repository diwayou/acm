package com.diwayou.reactive;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.SneakyThrows;

/**
 * https://github.com/ReactiveX/RxJava
 */
public class RxJavaBasic {
    @SneakyThrows
    public static void main(String[] args) {
        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v * v)
                .sequential()
                .blockingSubscribe(System.out::println);
    }
}
