package com.diwayou.web.store;

public class StoreQuery<T> extends Page {

    private T query;

    public StoreQuery(T query) {
        this.query = query;
    }

    public static <T> StoreQuery<T> create(T query) {
        return new StoreQuery<>(query);
    }
    public T getQuery() {
        return query;
    }

    public StoreQuery setQuery(T query) {
        this.query = query;
        return this;
    }
}
