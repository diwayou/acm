package com.diwayou.web.scheduler;

import java.io.Closeable;

public interface Scheduler<T> extends Closeable {

    boolean push(T t);
}
