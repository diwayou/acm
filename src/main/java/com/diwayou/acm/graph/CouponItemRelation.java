package com.diwayou.acm.graph;

import java.math.BigDecimal;
import java.util.Set;

public class CouponItemRelation {

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 优惠券需要满的金额
     */
    private BigDecimal maximumValue;

    /**
     * 优惠券优惠金额
     */
    private BigDecimal discountValue;

    /**
     * 优惠券适用的商品id集合
     */
    private Set<Long> itemIds;

    public CouponItemRelation() {
    }

    public CouponItemRelation(Long couponId, BigDecimal maximumValue, BigDecimal discountValue, Set<Long> itemIds) {
        this.couponId = couponId;
        this.maximumValue = maximumValue;
        this.discountValue = discountValue;
        this.itemIds = itemIds;
    }

    public Long getCouponId() {
        return couponId;
    }

    public CouponItemRelation setCouponId(Long couponId) {
        this.couponId = couponId;
        return this;
    }

    public BigDecimal getMaximumValue() {
        return maximumValue;
    }

    public CouponItemRelation setMaximumValue(BigDecimal maximumValue) {
        this.maximumValue = maximumValue;
        return this;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public CouponItemRelation setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
        return this;
    }

    public Set<Long> getItemIds() {
        return itemIds;
    }

    public CouponItemRelation setItemIds(Set<Long> itemIds) {
        this.itemIds = itemIds;
        return this;
    }

    @Override
    public String toString() {
        return "CouponItemRelation{" +
                "couponId=" + couponId +
                ", maximumValue=" + maximumValue +
                ", discountValue=" + discountValue +
                ", itemIds=" + itemIds +
                '}';
    }
}
