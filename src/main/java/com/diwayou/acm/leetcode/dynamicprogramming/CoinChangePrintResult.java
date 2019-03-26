package com.diwayou.acm.leetcode.dynamicprogramming;

// You are given coins of different denominations and a total amount of money amount. Write a function to compute
// the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by
// any combination of the coins, return -1.

//Example 1:
//coins = [1, 2, 5], amount = 11
//return 3 (11 = 5 + 5 + 1)

//Example 2:
//coins = [2], amount = 3
//return -1.

//Note:
//You may assume that you have an infinite number of each kind of coin.

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;

class CoinChangePrintResult {

    public static int coinChange(int[] coins, int amount) {
        if (amount < 1) {
            return 0;
        }

        // <amount, 组成该amount的面额值>
        Map<Integer, List<Integer>> dpDetail = Maps.newHashMap();

        List<Integer> result = coinChangeRecursive(coins, amount, dpDetail);

        if (result != null) {
            System.out.println(result);
        }

        return result == null ? -1 : result.size();
    }

    public static List<Integer> coinChangeRecursive(int[] coins, int amount, Map<Integer, List<Integer>> dpDetail) {
        if (amount < 0) {
            return null;
        }
        if (amount == 0) {
            return Collections.emptyList();
        }
        List<Integer> result = dpDetail.get(amount);
        if (result != null) {
            return result;
        }

        int min = Integer.MAX_VALUE;
        int minCoin = 0;
        List<Integer> minR = null;
        for (int coin : coins) {
            List<Integer> r = coinChangeRecursive(coins, amount - coin, dpDetail);
            if (r != null && r.size() < min) {
                min = 1 + r.size();

                minCoin = coin;
                minR = r;
            }
        }

        if (min != Integer.MAX_VALUE) {
            List<Integer> dpValue = Lists.newArrayList(minR);
            dpValue.add(minCoin);
            dpDetail.put(amount, dpValue);

            return dpValue;
        }

        return null;
    }

    public static void main(String[] args) {
        int[] coins = new int[] {1, 2, 5, 10, 20, 50, 100};
        System.out.println(coinChange(coins, 238));
        System.out.println(coinChange(coins, 533));

        coins = new int[] {2};
        System.out.println(coinChange(coins, 3));
    }
}
