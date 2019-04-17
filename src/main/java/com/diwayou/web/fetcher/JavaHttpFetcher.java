package com.diwayou.web.fetcher;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.domain.StreamPage;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class JavaHttpFetcher implements Fetcher {

    private HttpClient client;

    public JavaHttpFetcher() {
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    @Override
    public Page fetch(Request request) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(request.getUrl()))
                .timeout(Duration.ofSeconds(request.getTimeout()))
                .build();


        CompletableFuture<InputStream> future = client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body);

        return new StreamPage(request, future);
    }

    @Override
    public FetcherType getType() {
        return FetcherType.JAVA_HTTP;
    }
}
