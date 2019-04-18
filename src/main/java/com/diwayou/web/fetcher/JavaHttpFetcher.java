package com.diwayou.web.fetcher;

import com.diwayou.web.domain.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class JavaHttpFetcher implements Fetcher {

    private HttpClient client;

    public JavaHttpFetcher() {
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(3))
                .build();
    }

    @Override
    public Page fetch(Request request) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(request.getUrl()))
                .timeout(Duration.ofSeconds(request.getTimeout()))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3650.400 QQBrowser/10.4.3341.400")
                .build();


        HttpResponse<InputStream> response = null;
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
        } catch (IOException | InterruptedException e) {
            return new EmptyPage(request, e);
        }

        return new StreamPage(request, response);
    }

    @Override
    public FetcherType getType() {
        return FetcherType.JAVA_HTTP;
    }

    @Override
    public void close() {
        // ignore
    }
}
