package com.diwayou.web.scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowScheduler<T> implements Scheduler<T> {

    private SubmissionPublisher<T> publisher;

    public FlowScheduler(Executor executor, int maxCapacity) {
        this.publisher = new SubmissionPublisher<>(executor, maxCapacity);
    }

    @Override
    public boolean push(T t) {
        int lag = publisher.offer(t, (s, i) -> true);

        return lag >= 0;
    }

    @Override
    public void subscriber(Flow.Subscriber<T> subscriber) {
        publisher.subscribe(subscriber);
    }

    @Override
    public void close() throws Exception {
        publisher.close();
    }
}
