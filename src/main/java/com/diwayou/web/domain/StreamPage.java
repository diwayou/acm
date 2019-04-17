package com.diwayou.web.domain;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class StreamPage extends Page {

    private CompletableFuture<InputStream> bodyFuture;

    public StreamPage(Request request, CompletableFuture<InputStream> bodyFuture) {
        super(request);
        this.bodyFuture = bodyFuture;
    }

    @Override
    public String bodyAsString(int timeout) {
        try {
            InputStream is = bodyFuture.get(timeout, TimeUnit.SECONDS);

            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (InterruptedException | ExecutionException | TimeoutException | IOException e) {
            throw new RuntimeException("拉取消息失败", e);
        }
    }

    @Override
    public InputStream bodyAsInputStream(int timeout) {
        try {
            return bodyFuture.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("拉取消息失败", e);
        }
    }

    @Override
    public boolean isLoadFinish() {
        return bodyFuture.isDone();
    }
}
