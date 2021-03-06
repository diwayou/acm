package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/max-consecutive-ones/
 * <p>
 * 给定一个二进制数组， 计算其中最大连续1的个数。
 * <p>
 * 示例 1:
 * 输入: [1,1,0,1,1,1]
 * 输出: 3
 * 解释: 开头的两位和最后的三位都是连续1，所以最大连续1的个数是 3.
 * <p>
 * 注意：
 * 输入的数组只包含0 和1。
 * 输入数组的长度是正整数，且不超过 10,000。
 */
public class Lc485 {

    public int findMaxConsecutiveOnes(int[] nums) {
        int cnt = 0, max = 0;
        for (int n : nums) {
            if (n == 0) {
                if (cnt > max) {
                    max = cnt;
                }

                cnt = 0;
            } else {
                cnt++;
            }
        }

        return Math.max(max, cnt);
    }
}
