package com.diwayou.web.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FixedRejectThreadPoolExecutor extends ThreadPoolExecutor {

    public FixedRejectThreadPoolExecutor(int poolSize) {
        super(poolSize, poolSize, 5, TimeUnit.MINUTES, new SynchronousQueue<>(), Executors.defaultThreadFactory(), new AbortPolicy());
    }
}
