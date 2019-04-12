package com.diwayou.acm.net;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpStudy {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.csdn.net/"))
                //.timeout(Duration.ofSeconds(2))
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
