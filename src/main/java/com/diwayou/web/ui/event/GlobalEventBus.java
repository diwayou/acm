package com.diwayou.web.ui.event;

import com.google.common.eventbus.EventBus;

public class GlobalEventBus extends EventBus {

    private static final GlobalEventBus instance = new GlobalEventBus();

    private GlobalEventBus() {
        super("global");
    }

    public static GlobalEventBus one() {
        return instance;
    }
}
