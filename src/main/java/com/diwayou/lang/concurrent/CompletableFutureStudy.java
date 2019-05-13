package com.diwayou.lang.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureStudy {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Long> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1L;
        });
        CompletableFuture<Long> cf2 = CompletableFuture.supplyAsync(() -> 2L);
        CompletableFuture<Long> cf3 = CompletableFuture.supplyAsync(() -> 3L);

        CompletableFuture.allOf(cf1, cf2, cf3).join();

        System.out.println(cf1.get());
        System.out.println(cf2.get());
        System.out.println(cf3.get());
    }
}
