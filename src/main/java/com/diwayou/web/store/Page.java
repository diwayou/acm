package com.diwayou.web.store;

import com.google.common.base.Preconditions;

public class Page {
    /**
     * 从1开始
     */
    private int pageNum = 1;

    private int pageSize = 20;

    public int getPageNum() {
        return pageNum;
    }

    public Page setPageNum(int pageNum) {
        Preconditions.checkArgument(pageNum > 0, "页码从1开始!");

        this.pageNum = pageNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Page setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getStartOffset() {
        return (pageNum - 1) * pageSize;
    }
}
