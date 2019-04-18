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

    public Request getRequest() {
        return request;
    }

    public abstract String bodyAsString();

    public abstract InputStream bodyAsInputStream();

    public abstract int statusCode();
}
