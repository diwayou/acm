package com.diwayou.acm.graph;

import java.util.Collections;
import java.util.Set;

/**
 * 用户已经选择的券
 */
public class ChoseCoupon {

    /**
     * 券id
     */
    private Long couponId;

    /**
     * 该券占用的商品id集合
     */
    private Set<Long> itemIdSet = Collections.emptySet();

    public ChoseCoupon() {
    }

    public ChoseCoupon(Long couponId) {
        this.couponId = couponId;
    }

    public ChoseCoupon(Long couponId, Set<Long> itemIdSet) {
        this.couponId = couponId;
        this.itemIdSet = itemIdSet;
    }

    public Long getCouponId() {
        return couponId;
    }

    public ChoseCoupon setCouponId(Long couponId) {
        this.couponId = couponId;
        return this;
    }

    public Set<Long> getItemIdSet() {
        return itemIdSet;
    }

    public ChoseCoupon setItemIdSet(Set<Long> itemIdSet) {
        this.itemIdSet = itemIdSet;
        return this;
    }

    @Override
    public String toString() {
        return "ChoseCoupon{" +
                "couponId=" + couponId +
                ", itemIdSet=" + itemIdSet +
                '}';
    }
}
