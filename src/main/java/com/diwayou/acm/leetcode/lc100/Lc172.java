package com.diwayou.acm.leetcode.lc100;

/**
 * https://leetcode-cn.com/problems/factorial-trailing-zeroes/
 * <p>
 * 给定一个整数 n，返回 n! 结果尾数中零的数量。
 * <p>
 * 示例 1:
 * 输入: 3
 * 输出: 0
 * 解释:3! = 6, 尾数中没有零。
 * <p>
 * 示例2:
 * 输入: 5
 * 输出: 1
 * 解释:5! = 120, 尾数中有 1 个零.
 * <p>
 * 说明: 你算法的时间复杂度应为O(logn)。
 */
public class Lc172 {

    public int trailingZeroes(int n) {
        if (n == 0) {
            return 0;
        }

        int five = 0;
        while (n >= 5) {
            five += n / 5;
            n /= 5;
        }

        return five;
    }
}
