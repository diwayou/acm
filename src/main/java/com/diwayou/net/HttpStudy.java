package com.diwayou.net;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpStudy {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://sports.qq.com/nba/"))
                .timeout(Duration.ofSeconds(3))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(HttpStudy::processBody)
                .join();
    }

    private static void processBody(String body) {
        System.out.println(body);
    }
}
