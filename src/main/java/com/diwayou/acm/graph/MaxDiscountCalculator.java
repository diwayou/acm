package com.diwayou.acm.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
     * 最大优惠价值，例如订单付款金额不能减为0或者负
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

    /**
     * 推荐使用该构造函数，因为会根据券和商品数量计算参数，不容易出现性能问题
     *
     * @param items               商品列表
     * @param couponItemRelations 券与商品的关系
     * @param maxDiscount         优惠最高额度
     */
    public MaxDiscountCalculator(List<Item> items, List<CouponItemRelation> couponItemRelations, BigDecimal maxDiscount) {
        this(items, couponItemRelations, maxDiscount, DEFAULT_SUBSET_TOO_MANY_BREAK);
        this.subsetTooManyBreak = calculateBestBreak();
    }

    /**
     * 不推荐使用该构造函数，除非对参数非常了解，而且对数据性能有自己的考量
     *
     * @param items               商品列表
     * @param couponItemRelations 券与商品的关系
     * @param maxDiscount         优惠最高额度
     * @param subsetTooManyBreak  求券适用品满足条件的组合子集数量
     */
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

    /**
     * 计算最优的参数，都是本地构造数据测试的值
     */
    private int calculateBestBreak() {
        int calculateResult = FAST_CALCULATE;
        // 都是测试经验值
        if ((couponItemRelations.size() <= 6 && items.size() <= 30) ||
                (couponItemRelations.size() <= 9 && items.size() <= 8) ||
                (couponItemRelations.size() <= 3 && items.size() <= 40)) {
            calculateResult = DEFAULT_SUBSET_TOO_MANY_BREAK;
        }

        return calculateResult;
    }

    private void init() {
        // 商品价格从高到低排序
        items.sort(Comparator.comparing(Item::getPrice)
                .thenComparing(Item::getItemId)
                .reversed());
        // 优惠券面值从大到小排序
        couponItemRelations.sort(Comparator.comparing(CouponItemRelation::getDiscountValue)
                .reversed()
                .thenComparing(CouponItemRelation::getCouponId));

        itemMap = Maps.newHashMapWithExpectedSize(items.size());
        for (Item item : items) {
            itemMap.put(item.getItemId(), item);
        }

        couponItemRelationMap = Maps.newHashMapWithExpectedSize(couponItemRelations.size());
        for (CouponItemRelation relation : couponItemRelations) {
            couponItemRelationMap.put(relation.getCouponId(), relation);
        }
    }

    /**
     * 尽量计算最优的优惠组合，会根据商品和券计算出的参数选择不同的计算方式
     */
    public List<DiscountResult> computeBestDiscount() {
        if (this.subsetTooManyBreak == FAST_CALCULATE) {
            return fastCalculate();
        }

        return dpCalculate();
    }

    /**
     * 根据用户已经选择的券计算优惠
     *
     * @param choseCoupons 上次用户已选择的券，这个使用方可以存储在缓存里，也可以让客户端传过来（但是有被篡改风险）
     * @param couponId     用户该次选中的券
     * @return 优惠结果
     */
    public ChoseDiscountResult computeDiscount(List<ChoseCoupon> choseCoupons, Long couponId) {
        Preconditions.checkArgument(choseCoupons.size() < this.couponItemRelations.size());

        List<Item> remainItems = this.items.stream()
                .filter(i -> choseCoupons.stream()
                        .map(ChoseCoupon::getItemIdSet)
                        .noneMatch(s -> s.contains(i.getItemId())))
                .collect(Collectors.toList());

        CouponItemRelation relation = couponItemRelationMap.get(couponId);
        DiscountResult discountResult = computeOneCouponDiscount(remainItems, relation);
        if (!discountResult.canUse()) {
            throw new RuntimeException("选择的优惠券不满足条件");
        }

        ChoseCoupon curChoseCoupon = new ChoseCoupon(couponId, discountResult.getItemIds());
        List<ChoseCoupon> choseCouponResult = Lists.newArrayList(choseCoupons);
        choseCouponResult.add(curChoseCoupon);

        // 如果券都被选择了，直接返回结果
        if (choseCouponResult.size() == this.couponItemRelations.size()) {
            return new ChoseDiscountResult(choseCouponResult, Collections.emptyList());
        }

        remainItems = remainItems.stream()
                .filter(i -> !relation.getItemIds().contains(i.getItemId()))
                .collect(toList());
        // 如果没有剩余商品，直接返回结果
        if (remainItems.isEmpty()) {
            return new ChoseDiscountResult(choseCouponResult, Collections.emptyList());
        }

        List<CouponItemRelation> remainRelation = this.couponItemRelations.stream()
                .filter(r -> choseCouponResult.stream()
                        .map(ChoseCoupon::getCouponId)
                        .noneMatch(id -> r.getCouponId().equals(id)))
                .collect(toList());

        List<Long> couponCanChose = Lists.newArrayList();
        for (CouponItemRelation couponItemRelation : remainRelation) {
            DiscountResult dr = computeOneCouponDiscount(remainItems, couponItemRelation);
            if (dr.canUse()) {
                couponCanChose.add(dr.getCouponId());
            }
        }

        return new ChoseDiscountResult(choseCouponResult, couponCanChose);
    }

    /**
     * 使用动态规划尽量计算最优的优惠组合
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
     * @param items    商品列表, 需要按照价格、商品id排序的
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
                    k -> subsets(pItems, relation, this.subsetTooManyBreak, remainDiscount));

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

    /**
     * 计算只有一张券的时候优惠结果
     *
     * @param itemIds        商品id
     * @param couponIds      券id，只有一张
     * @param remainDiscount 剩余额度
     * @return 优惠结果列表，实际只有1个值或者空
     */
    private List<DiscountResult> computeBestDiscountOne(long[] itemIds, long[] couponIds, BigDecimal remainDiscount) {
        Preconditions.checkArgument(couponIds.length == 1, "券id数组长度必须1");

        CouponItemRelation relation = couponItemRelationMap.get(couponIds[0]);

        long[] pItemIds = filter(itemIds, relation.getItemIds().size(), i -> relation.getItemIds().contains(i));

        BigDecimal sum = BigDecimal.ZERO;

        Set<Long> usedItemIdSet = Sets.newHashSet();
        DiscountResult discountResult = DiscountResult.empty;
        for (long pItemId : pItemIds) {
            Item item = itemMap.get(pItemId);
            sum = sum.add(item.getPrice());
            usedItemIdSet.add(pItemId);

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
     * @param itemIds        商品子集，该商品数量会<=relation中关联品的数量
     * @param relation       券跟品的关系
     * @param limit          最多计算几个结果
     * @param remainDiscount 剩余额度
     * @return 所有满足该券的商品组合
     */
    private Collection<DiscountResult> subsets(long[] itemIds, CouponItemRelation relation, int limit, BigDecimal remainDiscount) {
        // 如果剩余金额不足，直接不能使用该张券
        if (remainDiscount.compareTo(relation.getDiscountValue()) <= 0) {
            return Collections.singletonList(DiscountResult.empty);
        }

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
}
