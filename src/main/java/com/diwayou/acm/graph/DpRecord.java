package com.diwayou.acm.graph;

import java.util.Arrays;

public class DpRecord {

    private long[] dpItem;

    private long[] dpRelation;

    public DpRecord() {
    }

    public DpRecord(long[] dpItem, long[] dpRelation) {
        this.dpItem = dpItem;
        this.dpRelation = dpRelation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DpRecord dpRecord = (DpRecord) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(dpItem, dpRecord.dpItem)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(dpRelation, dpRecord.dpRelation);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(dpItem);
        result = 31 * result + Arrays.hashCode(dpRelation);
        return result;
    }
}
