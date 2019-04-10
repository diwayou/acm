package com.diwayou.web.common;

import java.util.function.Consumer;

public class RuntimeUtil {

    public static void addShutdownHook(Consumer<Void> hook) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                hook.accept(null);
            }
        });
    }
}
