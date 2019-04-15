package com.diwayou.acm.coupon;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ItemSubsetInfo {

    public static final ItemSubsetInfo empty = new ItemSubsetInfo(Collections.emptyList(), BigDecimal.ZERO);

    private List<Item> items;

    private BigDecimal priceSum;

    public ItemSubsetInfo(List<Item> items, BigDecimal priceSum) {
        this.items = items;
        this.priceSum = priceSum;
    }

    public List<Item> getItems() {
        return items;
    }

    public ItemSubsetInfo setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public ItemSubsetInfo setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
        return this;
    }
}
