package com.diwayou.web.app;

import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;

public class App {
    public static void main(String[] args) throws Exception {
        Fetcher fetcher = FetcherFactory.one().getJavaHttpFetcher();

        Request request = new Request("https://www.oschina.net/");
        Page page = fetcher.fetch(request);

        System.out.println(page.bodyAsString());
    }
}
