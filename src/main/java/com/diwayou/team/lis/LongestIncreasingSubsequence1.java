package com.diwayou.team.lis;

/**
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/
 */
public class LongestIncreasingSubsequence1 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 6, 7, 9, 4, 10, 5, 6};
        System.out.println(new LongestIncreasingSubsequence1().lengthOfLIS(nums));
    }

    private int result = 1;

    private int[] cache;

    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        cache = new int[nums.length + 1];

        lengthOfLISHelper(nums, nums.length);

        return result;
    }

    private int lengthOfLISHelper(int[] nums, int n) {
        if (cache[n] > 0) {
            return cache[n];
        }

        if (n <= 1) {
            return n;
        }

        int max = 1;
        for (int i = 1; i < n; ++i) {
            int tmp = lengthOfLISHelper(nums, i);
            if (nums[i - 1] < nums[n - 1] && tmp + 1 > max) {
                max = tmp + 1;
            }
        }

        if (max > result) {
            result = max;
        }

        cache[n] = max;

        return max;
    }
}
