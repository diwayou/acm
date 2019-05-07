package com.diwayou.web.store;

import com.google.common.collect.Maps;

import java.util.Map;

public class PageStoreContext {

    private Map<String, Object> context = Maps.newHashMap();

    private PageStoreContext() {}

    public static PageStoreContext create() {
        return new PageStoreContext();
    }

    public PageStoreContext put(String key, Object attr) {
        context.put(key, attr);

        return this;
    }

    public Object get(String key) {
        return context.get(key);
    }
}
