package com.diwayou.team;

/**
 * https://leetcode-cn.com/problems/min-cost-climbing-stairs/
 * <p>
 * dp[i] = min(dp[i-2], dp[i-1]) + cost[i]
 */
public class MinCostClimbingStairs {
    public static void main(String[] args) {
        int[] cost = {0, 0, 1, 1};

        System.out.println(new MinCostClimbingStairs().minCostClimbingStairs(cost));
    }

    public int minCostClimbingStairs(int[] cost) {
        int[] dp = new int[cost.length];
        dp[0] = cost[0];
        dp[1] = cost[1];

        for (int i = 2; i < cost.length; i++) {
            dp[i] = Math.min(dp[i - 2], dp[i - 1]) + cost[i];
        }

        return Math.min(dp[cost.length - 1], dp[cost.length - 2]);
    }
}
