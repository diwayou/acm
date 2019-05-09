package com.diwayou.web.store;

public class StoreResult {

    public static final StoreResult empty = new StoreResult("");

    private String path;

    public StoreResult(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public StoreResult setPath(String path) {
        this.path = path;
        return this;
    }
}
