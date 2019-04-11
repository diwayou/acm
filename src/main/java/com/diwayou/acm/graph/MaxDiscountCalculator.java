package com.diwayou.acm.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * 根据订单传入的商品列表和券跟订单中商品的适用关系计算一个最大优惠的订单商品分配组合
 * 一个商品最多使用一张券，有可能多个商品才能使用一张券
 */
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
     * 最大优惠价值，例如订单不能减为负，够用就好
     */
    private BigDecimal maxDiscount;

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
    private Map<SubsetDpRecord, Collection<DiscountResult>> subsetDpRecords;

    /**
     * 当计算出组合过多，不计算所有结果，直接中断计算
     */
    private int subsetTooManyBreak;

    /**
     * 本地机器测试，3个的时候是性能和结果的一个权衡值，但是还受商品跟券数量的影响
     */
    private static final int DEFAULT_SUBSET_TOO_MANY_BREAK = 3;

    /**
     * 当是快速计算的时候，走简单计算逻辑
     */
    private static final int FAST_CALCULATE = 1;

    public MaxDiscountCalculator(List<Item> items, List<CouponItemRelation> couponItemRelations, BigDecimal maxDiscount) {
        this(items, couponItemRelations, maxDiscount, DEFAULT_SUBSET_TOO_MANY_BREAK);
        this.subsetTooManyBreak = calculateBestBreak();
    }

    public MaxDiscountCalculator(List<Item> items, List<CouponItemRelation> couponItemRelations, BigDecimal maxDiscount, int subsetTooManyBreak) {
        Preconditions.checkArgument(subsetTooManyBreak > 0, "subsetTooManyBreak必须大于0");
        Preconditions.checkNotNull(maxDiscount, "最大优惠金额不能为空");
        Preconditions.checkArgument(BigDecimal.ZERO.compareTo(maxDiscount) < 0, "最大优惠金额必须>0");

        this.items = items;
        this.couponItemRelations = couponItemRelations;
        this.maxDiscount = maxDiscount;
        this.subsetTooManyBreak = subsetTooManyBreak;

        init();
    }

    private int calculateBestBreak() {
        int calculateResult = FAST_CALCULATE;
        // 都是测试经验值
        if ((couponItemRelations.size() <= 6 && items.size() <= 30) ||
                (couponItemRelations.size() <= 3 && items.size() <= 40)) {
            calculateResult = DEFAULT_SUBSET_TOO_MANY_BREAK;
        }

        return calculateResult;
    }

    private void init() {
        // 商品价格从高到低排序
        items.sort(Comparator.comparing(Item::getPrice).thenComparing(Item::getItemId).reversed());
        // 优惠券面值从大到小排序
        couponItemRelations.sort(Comparator.comparing(CouponItemRelation::getDiscountValue).reversed());

        itemMap = Maps.newHashMapWithExpectedSize(items.size());
        for (Item item : items) {
            itemMap.put(item.getItemId(), item);
        }

        couponItemRelationMap = Maps.newHashMapWithExpectedSize(couponItemRelations.size());
        for (CouponItemRelation relation : couponItemRelations) {
            couponItemRelationMap.put(relation.getCouponId(), relation);
        }
    }

    public List<DiscountResult> computeBestDiscount() {
        if (this.subsetTooManyBreak == FAST_CALCULATE) {
            return fastCalculate();
        }

        return dpCalculate();
    }

    /**
     * 使用动态规划计算尽量计算最优的优惠组合
     */
    private List<DiscountResult> dpCalculate() {
        // 只能大概评估下，这个数字太难预测了
        dpRecords = Maps.newHashMapWithExpectedSize(items.size() * couponItemRelations.size());
        subsetDpRecords = Maps.newHashMapWithExpectedSize(items.size() * 2);

        long[] itemIds = new long[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemIds[i] = items.get(i).getItemId();
        }
        long[] couponIds = new long[couponItemRelations.size()];
        for (int i = 0; i < couponItemRelations.size(); i++) {
            couponIds[i] = couponItemRelations.get(i).getCouponId();
        }

        return computeBestDiscount(itemIds, couponIds, this.maxDiscount);
    }

    /**
     * 快速计算优惠组合结果，每张券尽量用更少的商品满足，所以会对满足条件的商品按照商品价格从大到小排序
     */
    private List<DiscountResult> fastCalculate() {
        List<Item> items = Lists.newArrayList(this.items);

        List<DiscountResult> result = Lists.newArrayList();
        BigDecimal remain = this.maxDiscount;
        for (CouponItemRelation relation : couponItemRelations) {
            DiscountResult discountResult = computeOneCouponDiscount(items, relation);
            if (!discountResult.canUse()) {
                continue;
            }

            // 订单不能减为负值或者0，但是继续计算，一旦有更小面值的优惠券呢
            BigDecimal curRemain = remain.subtract(discountResult.getDiscount());
            if (curRemain.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            remain = curRemain;
            result.add(discountResult);

            // 过滤掉已经被使用的商品
            items = items.stream()
                    .filter(i -> !discountResult.getItemIds().contains(i.getItemId()))
                    .collect(toList());
        }

        return result;
    }

    /**
     * 根据传入的商品id计算该券是否能够使用
     *
     * @param items  商品列表, 需要按照价格、商品id排序的
     * @param relation 券商品关系
     * @return 该券是否满足优惠条件的结果
     */
    private DiscountResult computeOneCouponDiscount(List<Item> items, CouponItemRelation relation) {
        List<Item> pItems = items.stream()
                .filter(i -> relation.getItemIds().contains(i.getItemId()))
                .collect(toList());

        BigDecimal sum = BigDecimal.ZERO;

        for (int i = 0; i < pItems.size(); i++) {
            Item item = pItems.get(i);
            sum = sum.add(item.getPrice());

            if (sum.compareTo(relation.getMaximumValue()) >= 0) {
                List<Long> itemIds = pItems.subList(0, i + 1).stream()
                        .map(Item::getItemId)
                        .collect(toList());
                return new DiscountResult(relation.getCouponId(),
                        // 如果已经满足了，过滤掉后边的商品
                        Sets.newHashSet(itemIds),
                        relation.getDiscountValue());
            }
        }

        return DiscountResult.empty;
    }

    /**
     * 计算最大折扣的优惠券组合
     *
     * @param itemIds   需要计算的商品id
     * @param couponIds 需要计算的券id
     * @return 最大优惠的商品券使用关系
     */
    private List<DiscountResult> computeBestDiscount(long[] itemIds, long[] couponIds, BigDecimal remainDiscount) {
        if (itemIds.length <= 0 || couponIds.length <= 0 || remainDiscount.compareTo(BigDecimal.ZERO) <= 0) {
            return Collections.emptyList();
        }

        List<DiscountResult> dpResult = dpRecords.get(new DpRecord(itemIds, couponIds));
        if (dpResult != null) {
            return dpResult;
        }

        // 优化只有一张券的情况
        if (couponIds.length == 1) {
            return computeBestDiscountOne(itemIds, couponIds, remainDiscount);
        }

        List<DiscountResult> result = Collections.emptyList();
        BigDecimal maxDiscount = BigDecimal.ZERO;
        for (long couponId : couponIds) {
            CouponItemRelation relation = couponItemRelationMap.get(couponId);

            long[] remainRelation = filter(couponIds, couponIds.length - 1, r -> !r.equals(relation.getCouponId()));

            long[] pItems = filter(itemIds, relation.getItemIds().size(), i -> relation.getItemIds().contains(i));

            Collection<DiscountResult> subsets = subsetDpRecords.computeIfAbsent(new SubsetDpRecord(pItems, couponId),
                    k -> subsets(pItems, relation, this.subsetTooManyBreak));

            for (DiscountResult discountResult : subsets) {
                List<DiscountResult> bestDiscountResults;
                if (discountResult.canUse()) {
                    long[] remainItems = filter(itemIds, itemIds.length - discountResult.getItemIds().size(),
                            i -> !discountResult.getItemIds().contains(i));

                    bestDiscountResults = computeBestDiscount(remainItems, remainRelation, remainDiscount.subtract(discountResult.getDiscount()));
                } else {
                    bestDiscountResults = computeBestDiscount(itemIds, remainRelation, remainDiscount);
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

                if (sum.compareTo(maxDiscount) > 0 &&
                        // 不能0元订单
                        sum.compareTo(remainDiscount) < 0) {
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

    private List<DiscountResult> computeBestDiscountOne(long[] itemIds, long[] couponIds, BigDecimal remainDiscount) {
        CouponItemRelation relation = couponItemRelationMap.get(couponIds[0]);

        long[] pItems = filter(itemIds, relation.getItemIds().size(), i -> relation.getItemIds().contains(i));

        BigDecimal sum = BigDecimal.ZERO;

        Set<Long> usedItemIdSet = Sets.newHashSet();
        DiscountResult discountResult = DiscountResult.empty;
        for (int i = 0; i < pItems.length; i++) {
            Item item = itemMap.get(pItems[i]);
            sum = sum.add(item.getPrice());
            usedItemIdSet.add(pItems[i]);

            if (sum.compareTo(relation.getMaximumValue()) >= 0) {
                discountResult = new DiscountResult(relation.getCouponId(),
                        usedItemIdSet,
                        relation.getDiscountValue());

                break;
            }
        }

        List<DiscountResult> result = Collections.emptyList();
        // 不能0元订单
        if (discountResult.canUse() && remainDiscount.compareTo(discountResult.getDiscount()) > 0) {
            result = Collections.singletonList(discountResult);
        }

        dpRecords.put(new DpRecord(itemIds, couponIds), result);

        return result;
    }

    /**
     * 从arr中过滤满足filter条件的数据到一个新数组中，如果预估的数组长度不足结果集，直接报错
     *
     * @param arr            被过滤的数组
     * @param estimateLength 预估的结果集大小
     * @param filter         过滤条件
     * @return 过滤后的结果数组
     */
    private long[] filter(long[] arr, int estimateLength, Predicate<Long> filter) {
        estimateLength = Math.min(arr.length, estimateLength);

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

    /**
     * 求满足券条件的商品组合子集
     *
     * @param itemIds  商品子集，该商品数量会<=relation中关联品的数量
     * @param relation 券跟品的关系
     * @param limit    最多计算几个结果
     * @return 所有满足该券的商品组合
     */
    private Collection<DiscountResult> subsets(long[] itemIds, CouponItemRelation relation, int limit) {
        List<DiscountResult> result = Lists.newArrayList();
        subsetsHelperFast(result, Lists.newArrayListWithCapacity(itemIds.length), itemIds, 0, relation, relation.getMaximumValue(), limit);

        // 添加一个不使用当前券的场景
        result.add(DiscountResult.empty);

        return result;
    }

    /**
     * 快速求满足券条件的子集，提前中断计算，更快
     *
     * @param result   满足条件的子集合
     * @param list     当前计算的商品子集前缀
     * @param itemIds  需要求子集的商品集合
     * @param start    当前计算位置，可以理解为递归层次
     * @param relation 需要计算条件的优惠券品关系
     * @param remain   当前剩余的条件值，如果该值<=0，就说明券满多少的条件已经满足了
     */
    private void subsetsHelperFast(Collection<DiscountResult> result, ArrayList<Long> list,
                                   long[] itemIds,
                                   int start,
                                   CouponItemRelation relation,
                                   BigDecimal remain, int limit) {
        if (remain.compareTo(BigDecimal.ZERO) <= 0 && result.size() < limit) {
            DiscountResult discountResult = new DiscountResult(relation.getCouponId(),
                    Sets.newHashSet(list),
                    relation.getDiscountValue());
            result.add(discountResult);

            return;
        }

        if (result.size() > limit) {
            return;
        }

        for (int i = start; i < itemIds.length; i++) {
            list.add(itemIds[i]);
            Item item = itemMap.get(itemIds[i]);
            BigDecimal curRemain = remain.subtract(item.getPrice());

            subsetsHelperFast(result, list, itemIds, i + 1, relation, curRemain, limit);

            list.remove(list.size() - 1);
        }
    }

    /**
     * 求满足券条件的子集，该算法使用枚举，没有提前中断，所以会慢很多，而且结果集需要是Set，因为当把多余品过滤掉的时候有可能计算出来的结果重复
     *
     * @param result   满足条件的子集合
     * @param list     当前计算的商品子集前缀
     * @param itemIds  需要求子集的商品集合
     * @param start    当前计算位置，可以理解为递归层次
     * @param relation 需要计算条件的优惠券品关系
     */
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

    /**
     * 计算该商品集合是否满足当前券
     *
     * @param relation 券跟商品的关系
     * @param itemIds  当前需要计算的商品列表
     * @return 是否满足券条件的结果
     */
    private DiscountResult calcDiscount(CouponItemRelation relation, List<Long> itemIds) {
        BigDecimal sum = BigDecimal.ZERO;

        for (int i = 0; i < itemIds.size(); i++) {
            Item item = itemMap.get(itemIds.get(i));
            sum = sum.add(item.getPrice());

            if (sum.compareTo(relation.getMaximumValue()) >= 0) {
                return new DiscountResult(relation.getCouponId(),
                        // 如果已经满足了，过滤掉后边的商品
                        Sets.newHashSet(itemIds.subList(0, i + 1)),
                        relation.getDiscountValue());
            }
        }

        return DiscountResult.empty;
    }
}
