package com.diwayou.web.domain;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class StringPage extends Page {

    private String body;

    public StringPage(Request request, String body) {
        super(request);
        this.body = body;
    }

    @Override
    public String bodyAsString(int timeout) {
        return body;
    }

    @Override
    public InputStream bodyAsInputStream(int timeout) {
        return new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean isLoadFinish() {
        return true;
    }
}
