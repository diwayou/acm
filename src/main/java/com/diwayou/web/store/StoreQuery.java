package com.diwayou.web.store;

import com.google.common.base.Preconditions;
import org.apache.lucene.search.Query;

public class StoreQuery {

    private Query query;

    /**
     * 从1开始
     */
    private int pageNum;

    private int pageSize;

    public Query getQuery() {
        return query;
    }

    public StoreQuery setQuery(Query query) {
        this.query = query;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public StoreQuery setPageNum(int pageNum) {
        Preconditions.checkArgument(pageNum > 0, "页码从1开始!");

        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public StoreQuery setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getStartOffset() {
        return (pageNum - 1) * pageSize;
    }
}
