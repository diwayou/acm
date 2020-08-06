package com.diwayou.web.app;

import com.diwayou.web.crawl.Spider;
import com.diwayou.web.crawl.SpiderBuilder;
import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;
import com.diwayou.web.domain.RequestScript;
import com.diwayou.web.fetcher.Fetcher;
import com.diwayou.web.fetcher.FetcherFactory;
import com.google.common.collect.Lists;

import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {
        Fetcher fetcher = FetcherFactory.one().getJavaHttpFetcher();

        Request request = new Request("https://www.oschina.net/");
        Page page = fetcher.fetch(request);

        System.out.println(page.bodyAsString());
    }
}
