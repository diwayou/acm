package com.diwayou.acm.graph;

import java.util.Arrays;

public class SubsetDpRecord {

    private long[] items;

    private long couponId;

    public SubsetDpRecord(long[] items, long couponId) {
        this.items = items;
        this.couponId = couponId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubsetDpRecord that = (SubsetDpRecord) o;

        if (couponId != that.couponId) return false;
        return Arrays.equals(items, that.items);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(items);
        result = 31 * result + (int) (couponId ^ (couponId >>> 32));
        return result;
    }
}
