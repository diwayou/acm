package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/range-sum-query-immutable/
 * <p>
 * 给定一个整数数组 nums，求出数组从索引i到j(i≤j) 范围内元素的总和，包含i, j两点。
 * <p>
 * 示例：
 * 给定 nums = [-2, 0, 3, -5, 2, -1]，求和函数为 sumRange()
 * <p>
 * sumRange(0, 2) -> 1
 * sumRange(2, 5) -> -1
 * sumRange(0, 5) -> -3
 * <p>
 * 说明:
 * 你可以假设数组不可变。
 * 会多次调用sumRange方法。
 */
public class Lc303 {

    class NumArray {

        private int[] nums;

        public NumArray(int[] nums) {
            this.nums = nums;
        }

        public int sumRange(int i, int j) {
            int sum = 0;
            for (int k = i; k <= j; k++) {
                sum += nums[k];
            }

            return sum;
        }
    }

    class NumArray1 {
        int[] nums;
        int[] dp;

        public NumArray1(int[] nums) {
            this.nums = nums;
            if (nums.length > 0) {
                dp = new int[nums.length];
                dp[0] = nums[0];
                for (int i = 1; i < nums.length; i++) {
                    dp[i] = nums[i] + dp[i - 1];
                }
            }
        }

        public int sumRange(int i, int j) {
            if (i >= 0 && j < nums.length) {
                return dp[j] - dp[i] + nums[i];
            }

            return 0;
        }
    }
}
