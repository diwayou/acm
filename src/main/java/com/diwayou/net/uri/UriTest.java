package com.diwayou.net.uri;

import java.net.URI;

public class UriTest {
    public static void main(String[] args) {
        URI uri = URI.create("http://www.baidu.com/p/q?a=1&b=2");
        System.out.println(uri.getHost());
        System.out.println(uri.getPort());
        System.out.println(uri.getPath());
    }
}
