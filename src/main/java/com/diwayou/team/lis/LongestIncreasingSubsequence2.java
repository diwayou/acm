package com.diwayou.team.lis;

/**
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/
 */
public class LongestIncreasingSubsequence2 {
    public static void main(String[] args) {
        int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(new LongestIncreasingSubsequence2().lengthOfLIS(nums));
    }

    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        // 下标为当前长度，value为当前下标对应的最长递增子序列长度
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int result = 1;
        int max;
        for (int i = 1; i < nums.length; ++i) {
            max = 1;
            for (int j = i - 1; j >= 0; --j) {
                if (nums[i] > nums[j]) {
                    max = Math.max(max, 1 + dp[j]);
                }
            }

            dp[i] = max;
            result = Math.max(result, dp[i]);
        }

        return result;
    }
}
