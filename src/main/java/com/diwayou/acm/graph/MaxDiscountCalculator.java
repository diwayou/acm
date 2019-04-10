package com.diwayou.acm.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MaxDiscountCalculator {

    /**
     * 商品信息列表
     */
    private List<Item> items;

    /**
     * 券商品关联信息列表
     */
    private List<CouponItemRelation> couponItemRelations;

    /**
     * <商品id, 商品信息>
     */
    private Map<Long, Item> itemMap;

    /**
     * <券id, 券信息>
     */
    private Map<Long, CouponItemRelation> couponItemRelationMap;

    /**
     * 缓存计算结果
     */
    private Map<DpRecord, List<DiscountResult>> dpRecords;

    /**
     * 缓存子集计算结果
     */
    private Map<SubsetDpRecord, Set<DiscountResult>> subsetDpRecords;

    /**
     * 当某张券满足条件商品组合过多，按照组合大小排序后留下的数量
     */
    private int subsetTooManyLimit;

    /**
     * 当计算出组合过多，不计算所有结果，直接中断计算
     */
    private int subsetTooManyBreak = 30;

    public MaxDiscountCalculator(List<Item> items, List<CouponItemRelation> couponItemRelations) {
        this(items, couponItemRelations, 10);
    }

    public MaxDiscountCalculator(List<Item> items, List<CouponItemRelation> couponItemRelations, int subsetTooManyLimit) {
        this.items = items;
        this.couponItemRelations = couponItemRelations;
        this.subsetTooManyLimit = Math.min(subsetTooManyLimit, subsetTooManyBreak);

        init();
    }

    private void init() {
        items.sort(Comparator.comparing(Item::getPrice).thenComparing(Item::getItemId).reversed());
        couponItemRelations.sort(Comparator.comparing(CouponItemRelation::getCouponId));

        itemMap = Maps.newHashMapWithExpectedSize(items.size());
        for (Item item : items) {
            itemMap.put(item.getItemId(), item);
        }

        couponItemRelationMap = Maps.newHashMapWithExpectedSize(couponItemRelations.size());
        for (CouponItemRelation relation : couponItemRelations) {
            couponItemRelationMap.put(relation.getCouponId(), relation);
        }

        // 只能大概评估下，这个数字太难预测了
        dpRecords = Maps.newHashMapWithExpectedSize(items.size() * couponItemRelations.size());
        subsetDpRecords = Maps.newHashMapWithExpectedSize(items.size() * 2);
    }

    public List<DiscountResult> computeBestDiscount() {
        long[] itemIds = new long[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemIds[i] = items.get(i).getItemId();
        }
        long[] couponIds = new long[couponItemRelations.size()];
        for (int i = 0; i < couponItemRelations.size(); i++) {
            couponIds[i] = couponItemRelations.get(i).getCouponId();
        }

        return computeBestDiscount(itemIds, couponIds);
    }

    /**
     * 计算最大折扣的优惠券组合
     */
    private List<DiscountResult> computeBestDiscount(long[] itemIds, long[] couponIds) {
        if (itemIds.length <= 0 || couponIds.length <= 0) {
            return Collections.emptyList();
        }

        List<DiscountResult> dpResult = dpRecords.get(new DpRecord(itemIds, couponIds));
        if (dpResult != null) {
            System.out.println("命中缓存");
            return dpResult;
        }

        List<DiscountResult> result = Collections.emptyList();
        BigDecimal maxDiscount = BigDecimal.ZERO;
        for (long couponId : couponIds) {
            CouponItemRelation relation = couponItemRelationMap.get(couponId);

            long[] remainRelation = filter(couponIds, couponIds.length - 1, r -> !r.equals(relation.getCouponId()));

            long[] pItems = filter(itemIds, relation.getItemIds().size(), i -> relation.getItemIds().contains(i));
            Set<DiscountResult> subsets = subsetDpRecords.computeIfAbsent(new SubsetDpRecord(pItems, couponId), k -> subsets(pItems, relation));

            for (DiscountResult discountResult : subsets) {
                List<DiscountResult> bestDiscountResults;
                if (discountResult.canUse()) {
                    long[] remainItems = filter(itemIds, itemIds.length - discountResult.getItemIds().size(),
                            i -> !discountResult.getItemIds().contains(i));

                    bestDiscountResults = computeBestDiscount(remainItems, remainRelation);
                } else {
                    bestDiscountResults = computeBestDiscount(itemIds, remainRelation);
                }

                BigDecimal sum = BigDecimal.ZERO;

                if (!bestDiscountResults.isEmpty()) {
                    sum = bestDiscountResults.stream()
                            .map(DiscountResult::getDiscount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }


                if (discountResult.canUse()) {
                    sum = sum.add(discountResult.getDiscount());
                }

                if (sum.compareTo(maxDiscount) > 0) {
                    List<DiscountResult> tmpResult = Lists.newArrayList(bestDiscountResults);
                    if (discountResult.canUse()) {
                        tmpResult.add(discountResult);
                    }

                    result = tmpResult;
                    maxDiscount = sum;
                }
            }
        }

        dpRecords.put(new DpRecord(itemIds, couponIds), result);

        return result;
    }

    private long[] filter(long[] arr, int estimateLength, Predicate<Long> filter) {
        long[] tmpArr = new long[estimateLength];
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (filter.test(arr[i])) {
                tmpArr[j++] = arr[i];
            }

            if (j > estimateLength) {
                throw new IllegalArgumentException("estimateLength评估过小");
            }
        }
        if (j == estimateLength) {
            return tmpArr;
        }

        return Arrays.copyOf(tmpArr, j);
    }

    private Set<DiscountResult> subsets(long[] itemIds, CouponItemRelation relation) {
        Set<DiscountResult> result = Sets.newHashSet();

        subsetsHelper(result, Lists.newArrayListWithCapacity(itemIds.length), itemIds, 0, relation);

        // 添加一个不使用当前券的场景
        result.add(DiscountResult.empty);

        if (result.size() > subsetTooManyLimit) {
            result = result.stream()
                    .sorted(Comparator.comparing(r -> r.getItemIds().size()))
                    .limit(subsetTooManyLimit)
                    .collect(Collectors.toSet());
        }

        return result;
    }

    private void subsetsHelper(Set<DiscountResult> result, ArrayList<Long> list, long[] itemIds, int start, CouponItemRelation relation) {
        if (result.size() > subsetTooManyBreak) {
            return;
        }

        DiscountResult discountResult = calcDiscount(relation, list);
        if (discountResult.canUse()) {
            result.add(discountResult);
        }

        for (int i = start; i < itemIds.length; i++) {
            list.add(itemIds[i]);

            subsetsHelper(result, list, itemIds, i + 1, relation);

            list.remove(list.size() - 1);
        }
    }

    private DiscountResult calcDiscount(CouponItemRelation relation, List<Long> itemIds) {
        BigDecimal sum = BigDecimal.ZERO;

        for (int i = 0; i < itemIds.size(); i++) {
            Item item = itemMap.get(itemIds.get(i));
            sum = sum.add(item.getPrice());

            if (sum.compareTo(relation.getMaximumValue()) >= 0) {
                return new DiscountResult()
                        .setCouponId(relation.getCouponId())
                        .setDiscount(relation.getDiscountValue())
                        // 如果已经满足了，过滤掉后边的商品
                        .setItemIds(Sets.newHashSet(itemIds.subList(0, i + 1)));
            }
        }

        return new DiscountResult();
    }
}
