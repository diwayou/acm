package com.diwayou.web.domain;

import java.net.http.HttpHeaders;
import java.util.Collections;

public class EmptyPage extends Page {

    private Exception exception;

    public EmptyPage(Request request) {
        super(request);
    }

    public EmptyPage(Request request, Exception exception) {
        super(request);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String bodyAsString() {
        return "";
    }

    @Override
    public byte[] bodyAsByteArray() {
        return new byte[0];
    }

    @Override
    public int statusCode() {
        return 0;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return HttpHeaders.of(Collections.emptyMap(), (a, b) -> true);
    }
}
