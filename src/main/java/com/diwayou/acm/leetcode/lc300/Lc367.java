package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/valid-perfect-square/
 *
 * 给定一个正整数 num，编写一个函数，如果 num 是一个完全平方数，则返回 True，否则返回 False。
 *
 * 说明：不要使用任何内置的库函数，如 sqrt。
 *
 * 示例 1：
 * 输入：16
 * 输出：True
 *
 * 示例 2：
 * 输入：14
 * 输出：False
 */
public class Lc367 {

    public boolean isPerfectSquare(int num) {
        long low = 1;
        long high = num;
        long square;
        while (low <= high) {
            long mid = (low + high) / 2;
            square = mid * mid;
            if (square > num) {
                high = mid - 1;
            } else if (square < num) {
                low = mid + 1;
            } else {
                return true;
            }
        }

        return false;
    }
}
