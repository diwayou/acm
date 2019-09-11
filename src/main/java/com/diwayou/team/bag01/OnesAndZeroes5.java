package com.diwayou.team.bag01;

/**
 * https://leetcode-cn.com/problems/ones-and-zeroes/
 * https://juejin.im/post/5b40c99ee51d45190b615f33
 */
public class OnesAndZeroes5 {
    public static void main(String[] args) {
        String[] strs = new String[]{"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;

        System.out.println(new OnesAndZeroes5().findMaxForm(strs, m, n));
    }

    public int findMaxForm(String[] strs, int m, int n) {
        if (strs.length == 0) {
            return 0;
        }

        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int zeroes = 0, ones = 0;

            for (int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) == '0') {
                    ++zeroes;
                } else {
                    ++ones;
                }
            }

            for (int j = m; j >= zeroes; --j) {
                for (int k = n; k >= ones; --k) {
                    dp[j][k] = Math.max(dp[j][k], 1 + dp[j - zeroes][k - ones]);
                }
            }
        }
        return dp[m][n];
    }
}
