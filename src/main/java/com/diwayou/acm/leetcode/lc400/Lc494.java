package com.diwayou.acm.leetcode.lc400;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/target-sum/
 */
public class Lc494 {

    public static void main(String[] args) {
        System.out.println(new Lc494().findTargetSumWays5(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0));
        System.out.println(new Lc494().findTargetSumWays5(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println(new Lc494().findTargetSumWays5(new int[]{1000}, -1000));
    }

    public int findTargetSumWays(int[] nums, int S) {
        if (nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        if (Math.abs(S) > sum) {
            return 0;
        }

        int[][] cache = new int[nums.length][sum + Math.abs(S) + 1];
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], -1);
        }
        return findTargetSumWaysHelper(nums, nums.length - 1, S, cache);
    }

    private int findTargetSumWaysHelper(int[] nums, int n, int s, int[][] cache) {
        if (n < 0) {
            if (s == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        if (cache[n][Math.abs(s)] != -1) {
            return cache[n][Math.abs(s)];
        }

        int re = findTargetSumWaysHelper(nums, n - 1, s + nums[n], cache) +
                findTargetSumWaysHelper(nums, n - 1, s - nums[n], cache);
        cache[n][Math.abs(s)] = re;

        return re;
    }

    public int findTargetSumWays3(int[] nums, int S) {
        if (nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        if (Math.abs(S) > sum) {
            return 0;
        }

        int sumLen = sum + Math.abs(S);
        int[][] dp = new int[nums.length + 1][sumLen + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= sumLen; j++) {
                dp[i][j] += dp[i - 1][Math.abs(j - nums[i - 1])];
                if (j + nums[i - 1] <= sumLen) {
                    dp[i][j] += dp[i - 1][j + nums[i - 1]];
                }
            }
        }

        return dp[nums.length][Math.abs(S)];
    }

    public int findTargetSumWays4(int[] nums, int S) {
        if (nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        if (Math.abs(S) > sum) {
            return 0;
        }

        int sumLen = sum + Math.abs(S);
        int[][] dp = new int[2][sumLen + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= sumLen; j++) {
                dp[i%2][j] = dp[(i - 1)%2][Math.abs(j - nums[i - 1])];
                if (j + nums[i - 1] <= sumLen) {
                    dp[i%2][j] += dp[(i - 1)%2][j + nums[i - 1]];
                }
            }
        }

        return dp[nums.length%2][Math.abs(S)];
    }

    public int findTargetSumWays5(int[] nums, int S) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        int sumS = sum + S;
        if (sum < S || sumS % 2 == 1) {
            return 0;
        }

        int sumP = sumS / 2;

        return findTargetSumWays5Helper(nums, nums.length - 1, sumP);
    }

    private int findTargetSumWays5Helper(int[] nums, int i, int sumP) {
        if (i < 0) {
            if (sumP == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        return findTargetSumWays5Helper(nums, i - 1, sumP) + findTargetSumWays5Helper(nums, i - 1, sumP - nums[i]);
    }

    public int findTargetSumWays1(int[] nums, int S) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        int sumS = sum + S;
        if (sum < S || sumS % 2 == 1) {
            return 0;
        }

        int sumP = sumS / 2;
        int[] dp = new int[sumP + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int i = sumP; i >= num; --i) {
                dp[i] += dp[i - num];
            }
        }

        return dp[sumP];
    }

    public int findTargetSumWays2(int[] nums, int S) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        int sumS = sum + S;
        if (sum < S || sumS % 2 == 1) {
            return 0;
        }

        int sumP = sumS / 2;

        int[][] dp = new int[nums.length + 1][sumP + 1];
        dp[0][0] = 1;
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= sumP; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i - 1]) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[nums.length][sumP];
    }
}
