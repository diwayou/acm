package com.diwayou.web.domain;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class StringPage extends Page {

    private String body;

    public StringPage(Request request, String body) {
        super(request);
        this.body = body;
    }

    @Override
    public String bodyAsString() {
        return body;
    }

    @Override
    public InputStream bodyAsInputStream() {
        return new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public int statusCode() {
        return 200;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return HttpHeaders.of(Collections.emptyMap(), (a, b) -> true);
    }
}
