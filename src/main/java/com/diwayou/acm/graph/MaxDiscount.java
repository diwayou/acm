package com.diwayou.acm.graph;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 计算下单优惠券组合的最大优惠值
 */
public class MaxDiscount {

    public static void main(String[] args) {
        Random random = new Random();
        int itemSize = 15;
        int maxPrice = 100;
        int couponSize = 7;

        for (int i = 0; i < 1000; i++) {
            List<DiscountResult> discountResults = getDiscountResults(random, itemSize, maxPrice, couponSize, true);
        }
    }

    private static List<DiscountResult> getDiscountResults(Random random, int itemSize, int maxPrice, int couponSize, boolean log) {
        List<Item> items = Lists.newArrayListWithCapacity(itemSize);
        for (long i = 1; i <= itemSize; i++) {
            Item item = new Item(i, BigDecimal.valueOf(1 + random.nextInt(maxPrice)));

            items.add(item);
        }

        List<CouponItemRelation> couponItemRelations = Lists.newArrayListWithCapacity(couponSize);
        for (long i = 1; i <= couponSize; i++) {
            int maximumValue = maxPrice / 2 + random.nextInt(maxPrice);
            int discountValue = 1 + random.nextInt(maximumValue - 1);

            Collections.shuffle(items, random);

            Set<Long> itemIdSet = items.subList(0, 1 + random.nextInt(items.size() / 2)).stream()
                    .map(Item::getItemId)
                    .collect(Collectors.toSet());

            CouponItemRelation relation = new CouponItemRelation(i,
                    BigDecimal.valueOf(maximumValue),
                    BigDecimal.valueOf(discountValue),
                    itemIdSet);

            couponItemRelations.add(relation);
        }


        long start = System.currentTimeMillis();
        MaxDiscountCalculator calculator = new MaxDiscountCalculator(items, couponItemRelations, 20);
        List<DiscountResult> discountResults = calculator.computeBestDiscount();
        if (log) {
            items.forEach(System.out::println);
            couponItemRelations.forEach(System.out::println);
            discountResults.forEach(System.out::println);
        }

        long end = System.currentTimeMillis();
        long cost = end - start;
        if (cost > 800) {
            System.out.println("计算消耗: " + cost);
        } else {
            System.out.println("计算消耗: " + cost);
        }

        return discountResults;
    }
}
