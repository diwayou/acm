package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/count-numbers-with-unique-digits/
 *
 * 给定一个非负整数 n，计算各位数字都不同的数字 x 的个数，其中 0 ≤ x < 10^n 。
 *
 * 示例:
 * 输入: 2
 * 输出: 91
 * 解释: 答案应为除去 11,22,33,44,55,66,77,88,99 外，在 [0,100) 区间内的所有数字。
 */
public class Lc357 {

    public static void main(String[] args) {
        System.out.println(new Lc357().countNumbersWithUniqueDigits1(2));
    }

    public int countNumbersWithUniqueDigits(int n) {
        if (n <= 0) {
            return 1;
        }

        if (n == 1) {
            return 10;
        }

        int re = 1;
        int cnt = Math.min(10, n);
        for (int i = 1; i <= cnt; i++) {
            re += 9 * countNumbersWithUniqueDigitsHelper(i - 1, 0);
        }

        return re;
    }

    private int countNumbersWithUniqueDigitsHelper(int n, int level) {
        if (n <= 0) {
            return 1;
        }

        return (9 - level) * countNumbersWithUniqueDigitsHelper(n - 1, level + 1);
    }

    public int countNumbersWithUniqueDigits1(int n) {
        if (n == 0) {
            return 1;
        }

        int re = 1;
        int dp = 1;
        int cnt = Math.min(10, n);
        for (int i = 1; i <= cnt; i++) {
            re += 9 * dp;
            dp = dp * (10 - i);
        }

        return re;
    }
}
