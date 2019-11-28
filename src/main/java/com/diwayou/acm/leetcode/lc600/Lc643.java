package com.diwayou.acm.leetcode.lc600;

/**
 * https://leetcode-cn.com/problems/maximum-average-subarray-i/
 *
 * 给定 n 个整数，找出平均数最大且长度为 k 的连续子数组，并输出该最大平均数。
 *
 * 示例 1:
 * 输入: [1,12,-5,-6,50,3], k = 4
 * 输出: 12.75
 * 解释: 最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
 *
 * 注意:
 * 1 <= k <= n <= 30,000。
 * 所给数据范围 [-10,000，10,000]。
 */
public class Lc643 {

    public static void main(String[] args) {
        System.out.println(new Lc643().findMaxAverage(new int[]{1,12,-5,-6,50,3}, 4));
    }

    public double findMaxAverage(int[] nums, int k) {
        int max = 0, cur;
        for (int i = 0; i < k; i++) {
            max += nums[i];
        }

        cur = max;
        for (int i = k; i < nums.length; i++) {
            cur -= nums[i - k];
            cur += nums[i];
            if (cur > max) {
                max = cur;
            }
        }

        return (double)max / k;
    }
}
