package com.diwayou.lang.concurrent;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

public class FlowStudy {
    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(ForkJoinPool.commonPool(), 1);

        List<String> subscribeNames = List.of("One", "Two");

        CountDownLatch latch = new CountDownLatch(subscribeNames.size());

        subscribeNames.forEach(name -> publisher.subscribe(new SimpleSubscribe(name, latch)));

        IntStream.rangeClosed(1, 5).forEach(i -> {
                    int lag = publisher.offer(i, (s, item) -> {
                        System.out.println(((SimpleSubscribe) s).getName() + "丢弃--- " + item);
                        return false;
                    });

                    System.out.println(lag);
                }
        );

        publisher.close();

        latch.await();
        System.out.println("Success");
    }

    private static class SimpleSubscribe implements Flow.Subscriber<Integer> {

        private Flow.Subscription subscription;

        private String name;

        private CountDownLatch latch;

        public SimpleSubscribe(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;

            this.subscription.request(1);
            System.out.println(String.format("name=%s onSubscribe", this.name));
        }

        @Override
        public void onNext(Integer item) {
            this.subscription.request(1);

            System.out.println(String.format("name=%s onNext item=%d", this.name, item));
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println(String.format("name=%s onError %s", this.name, throwable.toString()));
        }

        @Override
        public void onComplete() {
            System.out.println(String.format("name=%s onComplete", this.name));
            this.latch.countDown();
        }

        public String getName() {
            return name;
        }
    }
}
