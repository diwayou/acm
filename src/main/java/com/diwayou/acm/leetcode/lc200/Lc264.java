package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/ugly-number-ii/
 *
 * 编写一个程序，找出第 n 个丑数。
 * 丑数就是只包含质因数2, 3, 5 的正整数。
 *
 * 示例:
 * 输入: n = 10
 * 输出: 12
 * 解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
 * 说明:
 *
 * 1是丑数。
 * n不超过1690。
 */
public class Lc264 {

    public static void main(String[] args) {
        System.out.println(new Lc264().nthUglyNumber(1690));
    }

    public int nthUglyNumber(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        int i2, i3, i5;
        i2 = i3 = i5 = 0;
        for (int i = 1; i < n; i++) {
            dp[i] = Math.min(2 * dp[i2], Math.min(3 * dp[i3], 5 * dp[i5]));

            if (dp[i] >= 2 * dp[i2]) {
                i2++;
            }
            if (dp[i] >= 3 * dp[i3]) {
                i3++;
            }
            if (dp[i] >= 5 * dp[i5]) {
                i5++;
            }
        }

        return dp[n - 1];
    }
}
