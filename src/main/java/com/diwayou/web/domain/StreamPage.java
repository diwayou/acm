package com.diwayou.web.domain;

import com.diwayou.web.support.PageUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

public class StreamPage extends Page {

    private HttpResponse<InputStream> httpResponse;

    public StreamPage(Request request, HttpResponse<InputStream> httpResponse) {
        super(request);
        this.httpResponse = httpResponse;
    }

    @Override
    public String bodyAsString() {
        try {
            return IOUtils.toString(httpResponse.body(), PageUtil.getCharset(this));
        } catch (IOException e) {
            throw new RuntimeException("拉取消息失败", e);
        }
    }

    @Override
    public InputStream bodyAsInputStream() {
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
