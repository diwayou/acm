package com.diwayou.web.crawl;

/**
 * @author gaopeng
 * @date 2020/12/11
 */
public enum DbNamespace {
    Url(1),
    Waiting(2),
    Crawling(3),
    ;

    private int id;

    DbNamespace(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
