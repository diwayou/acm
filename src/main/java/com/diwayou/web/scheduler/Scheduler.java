package com.diwayou.web.scheduler;

import java.util.concurrent.Flow;

public interface Scheduler<T> extends AutoCloseable {

    boolean push(T t);

    void subscriber(Flow.Subscriber<T> subscriber);
}
