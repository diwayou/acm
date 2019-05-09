package com.diwayou.web.store;

import org.apache.lucene.document.Document;

import java.util.List;

public class QueryResult {

    private List<Document> docs;

    private int total;

    public QueryResult(List<Document> docs) {
        this.docs = docs;
    }

    public List<Document> getDocs() {
        return docs;
    }

    public QueryResult setDocs(List<Document> docs) {
        this.docs = docs;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public QueryResult setTotal(int total) {
        this.total = total;
        return this;
    }
}
