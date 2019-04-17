package com.diwayou.web.domain;

import java.io.InputStream;

public abstract class Page {

    private Request request;

    public Page(Request request) {
        this.request = request;
    }

    public Page setRequest(Request request) {
        this.request = request;
        return this;
    }

    public abstract String bodyAsString(int timeout);

    public abstract InputStream bodyAsInputStream(int timeout);

    public abstract boolean isLoadFinish();
}
