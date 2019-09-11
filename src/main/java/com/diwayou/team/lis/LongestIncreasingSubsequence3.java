package com.diwayou.team.lis;

/**
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/
 */
public class LongestIncreasingSubsequence3 {
    public static void main(String[] args) {
        int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(new LongestIncreasingSubsequence3().lengthOfLIS(nums));
    }

    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        // 下标为最长长度，value为当前最长长度子序列中最大的值
        int[] dp = new int[nums.length + 1];
        int end = 1;
        dp[end] = nums[0];
        int left, right, mid;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] < dp[1]) {
                dp[1] = nums[i];
            } else if (nums[i] > dp[end]) {
                dp[++end] = nums[i];
            } else {
                left = 1;
                right = end;
                while (left < right) {
                    mid = (left + right) / 2;
                    if (dp[mid] < nums[i]) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }

                dp[left] = nums[i];
            }
        }

        return end;
    }
}
