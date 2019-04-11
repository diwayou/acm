package com.diwayou.acm.graph;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 计算下单优惠券组合的最大优惠值
 */
public class MaxDiscount {

    public static void main(String[] args) {
        Random random = new Random();
        int itemSize = 20;
        int maxPrice = 100;
        int couponSize = 6;

        for (int i = 0; i < 10000; i++) {
            getDiscountResults(random, itemSize, maxPrice, couponSize, true, i);
        }
    }

    private static void getDiscountResults(Random random, int itemSize, int maxPrice, int couponSize, boolean log, int round) {
        List<Item> items = Lists.newArrayListWithCapacity(itemSize);
        for (long i = 1; i <= itemSize; i++) {
            Item item = new Item(i, BigDecimal.valueOf(1 + random.nextInt(maxPrice)));

            items.add(item);
        }

        List<CouponItemRelation> couponItemRelations = Lists.newArrayListWithCapacity(couponSize);
        for (long i = 1; i <= couponSize; i++) {
            int maximumValue = maxPrice / 3 + random.nextInt(maxPrice);
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

        int sum = couponItemRelations.stream()
                .map(CouponItemRelation::getDiscountValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .intValue();
        int maxDiscount = sum / 2 + random.nextInt(sum / 2);

        if (log) {
            items.sort(Comparator.comparing(Item::getItemId));
            items.forEach(System.out::println);
            couponItemRelations.forEach(System.out::println);
            System.out.println("discountSum = " + sum);
            System.out.println("maxDiscount = " + maxDiscount);
        }

        BigDecimal prev = null;
        List<Integer> breakList = List.of(1, 3);
        for (int startBreak : breakList) {
            Stopwatch stopwatch = Stopwatch.createUnstarted();
            stopwatch.start();

            MaxDiscountCalculator calculator = new MaxDiscountCalculator(items,
                    couponItemRelations,
                    BigDecimal.valueOf(10000),
                    startBreak);
            List<DiscountResult> discountResults = calculator.computeBestDiscount();

            stopwatch.stop();

            if (log) {
                discountResults.forEach(System.out::println);
                BigDecimal discount = discountResults.stream()
                        .map(DiscountResult::getDiscount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                System.out.println("Discount = " + discount);
            }

            long cost = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            if (cost > 400) {
                System.out.println(String.format("计算消耗超过: %d, break=%d", cost, startBreak));
                break;
            } else if (log) {
                System.out.println("计算消耗: " + cost);
            }


            if (prev == null) {
                prev = sum(discountResults);
            } else {
                BigDecimal cur = sum(discountResults);
                // 因为startBreak是1的时候，算法很简单，所以很有可能算错，但是2也有可能错
                if (!prev.equals(cur) && startBreak > 3) {
                    System.out.println(String.format("不相等break=%d, prev=%f, cur=%f", startBreak, prev, cur));
                    System.out.println();
                }
                prev = cur;
            }
        }

        System.out.println("执行一次完成 " + round);
    }

    private static BigDecimal sum(List<DiscountResult> discountResults) {
        return discountResults.stream()
                .map(DiscountResult::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
