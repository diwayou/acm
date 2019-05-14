package com.diwayou.web.scheduler;

import java.io.Closeable;
import java.util.Collections;
import java.util.List;

public interface Scheduler<T> extends Closeable {

    default void push(T t) {
        push(Collections.singletonList(t));
    }

    void push(List<T> list);
}
