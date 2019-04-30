package com.diwayou.web.domain;

import com.diwayou.web.support.PageUtil;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

public class StreamPage extends Page {

    private HttpResponse<byte[]> httpResponse;

    public StreamPage(Request request, HttpResponse<byte[]> httpResponse) {
        super(request);
        this.httpResponse = httpResponse;
    }

    @Override
    public String bodyAsString() {
        return new String(httpResponse.body(), PageUtil.getCharset(this));
    }

    @Override
    public byte[] bodyAsByteArray() {
        return httpResponse.body();
    }

    @Override
    public int statusCode() {
        return httpResponse.statusCode();
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return httpResponse.headers();
    }
}
