package com.diwayou.web.domain;

public enum FetcherType {
    /**
     * 基于java11的http client，能够抓取简单的html文本友好网站
     */
    JAVA_HTTP,
    /**
     * 基于openjfx的webview抓取数据，能够抓取一些html不友好的网站
     */
    FX_WEBVIEW
}
