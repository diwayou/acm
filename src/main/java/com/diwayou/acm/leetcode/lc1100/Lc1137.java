package com.diwayou.acm.leetcode.lc1100;

/**
 * https://leetcode-cn.com/problems/n-th-tribonacci-number/
 * <p>
 * 泰波那契序列Tn定义如下：
 * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0的条件下 Tn+3 = Tn + Tn+1 + Tn+2
 * 给你整数n，请返回第 n 个泰波那契数Tn 的值。
 * <p>
 * 示例 1：
 * 输入：n = 4
 * 输出：4
 * 解释：
 * T_3 = 0 + 1 + 1 = 2
 * T_4 = 1 + 1 + 2 = 4
 * <p>
 * 示例 2：
 * 输入：n = 25
 * 输出：1389537
 * <p>
 * 提示：
 * 0 <= n <= 37
 * 答案保证是一个 32 位整数，即answer <= 2^31 - 1。
 */
public class Lc1137 {

    public static void main(String[] args) {
        System.out.println(new Lc1137().tribonacci(25));
    }

    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n < 3) {
            return 1;
        }

        int a = 0, b = 1, c = 1, t, i = 2;
        while (i < n) {
            t = a + b + c;
            a = b;
            b = c;
            c = t;

            i++;
        }

        return c;
    }
}
