package com.diwayou.web.scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowScheduler<T> implements Scheduler<T> {

    private static final Logger log = Logger.getLogger(FlowScheduler.class.getName());

    private SubmissionPublisher<T> publisher;

    private Executor executor;

    private Consumer<T> processor;

    private String name;

    private Flow.Subscriber<T> subscriber;

    public FlowScheduler(String name, Executor executor, Consumer<T> processor) {
        this.name = name;
        this.publisher = new SubmissionPublisher<>();
        this.executor = executor;
        this.processor = processor;
        this.subscriber = new DefaultSubscriber();

        this.publisher.subscribe(subscriber);
    }

    @Override
    public boolean push(T t) {
        if (publisher.isClosed()) {
            return false;
        }

        int lag = publisher.offer(t, (s, i) -> false);

        return lag >= 0;
    }

    @Override
    public void close() {
        publisher.close();
    }

    private class DefaultSubscriber implements Flow.Subscriber<T> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;

            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            executor.execute(() -> {
                try {
                    processor.accept(item);
                } catch (Throwable ignore) {
                }
            });

            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            log.log(Level.WARNING, name + ": 处理错误", throwable);
        }

        @Override
        public void onComplete() {
            log.log(Level.INFO, name + ": 处理完成");
        }
    }
}
