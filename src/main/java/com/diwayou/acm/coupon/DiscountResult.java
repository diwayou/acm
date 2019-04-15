package com.diwayou.acm.coupon;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public class DiscountResult {

    public static final DiscountResult empty = new DiscountResult();

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 适用的商品列表
     */
    private Set<Long> itemIds = Collections.emptySet();

    /**
     * 折扣值
     */
    private BigDecimal discount = BigDecimal.ZERO;

    public DiscountResult() {
    }

    public DiscountResult(Long couponId, Set<Long> itemIds, BigDecimal discount) {
        this.couponId = couponId;
        this.itemIds = itemIds;
        this.discount = discount;
    }

    public Long getCouponId() {
        return couponId;
    }

    public DiscountResult setCouponId(Long couponId) {
        this.couponId = couponId;
        return this;
    }

    public Set<Long> getItemIds() {
        return itemIds;
    }

    public DiscountResult setItemIds(Set<Long> itemIds) {
        this.itemIds = itemIds;
        return this;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public DiscountResult setDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public boolean canUse() {
        return BigDecimal.ZERO.compareTo(discount) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountResult that = (DiscountResult) o;

        if (couponId != null ? !couponId.equals(that.couponId) : that.couponId != null) return false;
        if (itemIds != null ? !itemIds.equals(that.itemIds) : that.itemIds != null) return false;
        return discount.equals(that.discount);

    }

    @Override
    public int hashCode() {
        int result = couponId != null ? couponId.hashCode() : 0;
        result = 31 * result + (itemIds != null ? itemIds.hashCode() : 0);
        result = 31 * result + discount.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DiscountResult{" +
                "couponId=" + couponId +
                ", itemIds=" + itemIds +
                ", discount=" + discount +
                '}';
    }
}
