package com.diwayou.web.fetcher;

import com.diwayou.web.config.RobotConfig;
import com.diwayou.web.domain.*;

import java.io.IOException;
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
                .header("User-Agent", RobotConfig.getDefaultAgent())
                .build();


        HttpResponse<byte[]> response;
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
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
