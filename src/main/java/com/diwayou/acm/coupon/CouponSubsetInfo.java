package com.diwayou.acm.coupon;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class CouponSubsetInfo {

    public static final CouponSubsetInfo empty = new CouponSubsetInfo(Collections.emptyList(), BigDecimal.ZERO);

    private List<DiscountResult> discountResults;

    private List<Item> remainItems;

    private BigDecimal discount;

    public CouponSubsetInfo(List<DiscountResult> discountResults, BigDecimal discount) {
        this.discountResults = discountResults;
        this.discount = discount;
    }

    public List<DiscountResult> getDiscountResults() {
        return discountResults;
    }

    public CouponSubsetInfo setDiscountResults(List<DiscountResult> discountResults) {
        this.discountResults = discountResults;
        return this;
    }

    public List<Item> getRemainItems() {
        return remainItems;
    }

    public CouponSubsetInfo setRemainItems(List<Item> remainItems) {
        this.remainItems = remainItems;
        return this;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public CouponSubsetInfo setDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }
}
