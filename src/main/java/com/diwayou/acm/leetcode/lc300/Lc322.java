package com.diwayou.acm.leetcode.lc300;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/coin-change/
 *
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 *
 * 示例 1:
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 *
 * 示例 2:
 * 输入: coins = [2], amount = 3
 * 输出: -1
 *
 * 说明:
 * 你可以认为每种硬币的数量是无限的。
 */
public class Lc322 {

    public static void main(String[] args) {
        System.out.println(new Lc322().coinChange(new int[]{186,419,83,408}, 6249));
    }

    private int[] cache;

    public int coinChange3(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }

        if (cache == null) {
            cache = new int[amount + 1];
        } else if (cache[amount] != 0) {
            return cache[amount];
        }

        int re = Integer.MAX_VALUE, t;
        for (int i = coins.length - 1; i >= 0; i--) {
            if (coins[i] <= amount) {
                t = coinChange(coins, amount - coins[i]);
                if (t >= 0 && t + 1 < re) {
                    re = t + 1;
                }
            }
        }

        re = re == Integer.MAX_VALUE ? -1 : re;
        cache[amount] = re;

        return re;
    }

    public int coinChange1(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        int[] dp = new int[amount + 1];
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] <= amount) {
                dp[coins[i]] = 1;
            }
        }

        int min;
        for (int i = 1; i < dp.length; i++) {
            if (dp[i] != 0) {
                continue;
            }

            min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i > coins[j] && dp[i - coins[j]] != -1 && 1 + dp[i - coins[j]] < min) {
                    min = 1 + dp[i - coins[j]];
                }
            }
            dp[i] = min == Integer.MAX_VALUE ? -1 : min;
        }

        return dp[amount];
    }

    public int coinChange2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for (int i = 0; i < amount + 1; i++) {
            dp[i] = amount + 1;
        }
        dp[0] = 0;
        for (int i = 1; i < amount + 1; i++) {
            for (int coin : coins) {
                if (i - coin < 0) continue;
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }

        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }

    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        recursion(coins, amount, 0, coins.length - 1);
        return minCount == Integer.MAX_VALUE ? -1 : minCount;
    }

    int minCount = Integer.MAX_VALUE;

    void recursion(int[] coins, int amount, int count, int index) {
        if (index < 0 || count + amount / coins[index] >= minCount) {
            return;
        }

        if (amount % coins[index] == 0) {
            minCount = Math.min(minCount, count + amount / coins[index]);
            return;
        }

        for (int i = amount / coins[index]; i >= 0; i--) {
            recursion(coins, amount - i * coins[index], count + i, index - 1);
        }
    }
}
