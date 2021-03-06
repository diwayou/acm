package com.diwayou.acm.leetcode.lc700;

/**
 * https://leetcode-cn.com/problems/min-cost-climbing-stairs/
 * <p>
 * 数组的每个索引做为一个阶梯，第i个阶梯对应着一个非负数的体力花费值cost[i](索引从0开始)。
 * 每当你爬上一个阶梯你都要花费对应的体力花费值，然后你可以选择继续爬一个阶梯或者爬两个阶梯。
 * 您需要找到达到楼层顶部的最低花费。在开始时，你可以选择从索引为 0 或 1 的元素作为初始阶梯。
 * <p>
 * 示例1:
 * 输入: cost = [10, 15, 20]
 * 输出: 15
 * 解释: 最低花费是从cost[1]开始，然后走两步即可到阶梯顶，一共花费15。
 * <p>
 * 示例 2:
 * 输入: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
 * 输出: 6
 * 解释: 最低花费方式是从cost[0]开始，逐个经过那些1，跳过cost[3]，一共花费6。
 * <p>
 * 注意：
 * cost的长度将会在[2, 1000]。
 * 每一个cost[i] 将会是一个Integer类型，范围为[0, 999]
 */
public class Lc746 {

    public static void main(String[] args) {
        System.out.println(new Lc746().minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
    }

    public int minCostClimbingStairs(int[] cost) {
        int a = cost[0], b = cost[1];
        for (int i = 2; i < cost.length; i++) {
            int t = Math.min(a + cost[i], b + cost[i]);
            a = b;
            b = t;
        }

        return Math.min(a, b);
    }
}
