package com.diwayou.web.store;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Set;

public class MemoryUrlStore implements UrlStore {

    private Set<String> urlSet;

    public MemoryUrlStore() {
        this.urlSet = Collections.synchronizedSet(Sets.newHashSet());
    }

    @Override
    public boolean contain(String url) {
        return urlSet.contains(url);
    }

    @Override
    public void add(String url) {
        urlSet.add(url);
    }
}
