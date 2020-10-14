package com.diwayou.acm.leetcode.lc0;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/jump-game-ii/
 *
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 *
 * 示例:
 * 输入: [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 *     从下标为 0 跳到下标为 1 的位置，跳1步，然后跳3步到达数组的最后一个位置。
 *
 * 说明:
 * 假设你总是可以到达数组的最后一个位置。
 */
public class Lc45 {

    public static void main(String[] args) {
        System.out.println(new Lc45().jump(new int[]{2,3,1,1,4}));
    }

    public int jump1(int[] nums) {
        int[] cache = new int[nums.length];
        Arrays.fill(cache, -1);

        return jumpHelper(nums, nums.length - 1, cache);
    }

    private int jumpHelper(int[] nums, int i, int[] cache) {
        if (i == 0) {
            return 0;
        }

        if (cache[i] >= 0) {
            return cache[i];
        }

        int re = Integer.MAX_VALUE, t;
        for (int j = 0; j < i; j++) {
            if (nums[j] >= i - j) {
                t = 1 + jumpHelper(nums, j, cache);
                if (t < re) {
                    re = t;
                }
            }
            if (re == 1) {
                break;
            }
        }

        cache[i] = re;
        return re;
    }

    public int jump(int[] nums) {
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;

        int re;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = i;
            re = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (nums[j] >= i - j) {
                    re = Math.min(re, 1 + dp[j]);
                }

                if (re == 1) {
                    break;
                }
            }

            dp[i] = Math.min(dp[i], re);
        }

        return dp[nums.length - 1];
    }

    // 贪心。。。
    public int jump2(int[] nums) {
        if (nums.length < 2) return 0;

        int curMax = nums[0];
        int preMax = nums[0];
        int jumpMin = 1;
        for (int i = 0; i < nums.length; i++) {
            if (i > curMax) {
                jumpMin++;
                curMax = preMax;
            }
            // 牛逼思路
            if (preMax < nums[i] + i)
                preMax = nums[i] + i;
        }

        return jumpMin;
    }

    public int jump3(int[] nums) {
        if (nums.length <= 1) return 0;

        int[] steps = new int[nums.length];
        steps[0] = nums[0];
        int s = 0;

        if (steps[s] >= nums.length - 1)
            return s + 1;

        for (int i = 1; i < nums.length; i++) {
            if (i <= steps[s]) {
                steps[s + 1] = Math.max(steps[s + 1], i + nums[i]);
                if (steps[s + 1] >= nums.length - 1)
                    return s + 1 + 1;
                if (i == steps[s]) s++;
            } else {
                s = s + 1;
            }
        }

        return 0;
    }
}
