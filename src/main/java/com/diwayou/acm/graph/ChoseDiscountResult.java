package com.diwayou.acm.graph;

import java.util.List;

/**
 * 用户主动选择券的计算结果
 */
public class ChoseDiscountResult {

    /**
     * 用户已经选择的券
     */
    private List<ChoseCoupon> choseCoupons;

    /**
     * 剩余用户可以选择的券
     */
    private List<Long> couponCanChose;

    public ChoseDiscountResult() {
    }

    public ChoseDiscountResult(List<ChoseCoupon> choseCoupons, List<Long> couponCanChose) {
        this.choseCoupons = choseCoupons;
        this.couponCanChose = couponCanChose;
    }

    public List<ChoseCoupon> getChoseCoupons() {
        return choseCoupons;
    }

    public ChoseDiscountResult setChoseCoupons(List<ChoseCoupon> choseCoupons) {
        this.choseCoupons = choseCoupons;
        return this;
    }

    public List<Long> getCouponCanChose() {
        return couponCanChose;
    }

    public ChoseDiscountResult setCouponCanChose(List<Long> couponCanChose) {
        this.couponCanChose = couponCanChose;
        return this;
    }

    @Override
    public String toString() {
        return "ChoseDiscountResult{" +
                "choseCoupons=" + choseCoupons +
                ", couponCanChose=" + couponCanChose +
                '}';
    }
}
