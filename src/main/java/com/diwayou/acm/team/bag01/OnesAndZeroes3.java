package com.diwayou.acm.team.bag01;

/**
 * https://leetcode-cn.com/problems/ones-and-zeroes/
 * https://juejin.im/post/5b40c99ee51d45190b615f33
 */
public class OnesAndZeroes3 {
    public static void main(String[] args) {
        String[] strs = new String[]{"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;

        System.out.println(new OnesAndZeroes3().findMaxForm(strs, m, n));
    }

    public int findMaxForm(String[] strs, int m, int n) {
        if (strs.length == 0) {
            return 0;
        }

        int zeroes = 0, ones = 0;
        for (int i = 0; i < strs[0].length(); ++i) {
            if (strs[0].charAt(i) == '0') {
                ++zeroes;
            } else {
                ++ones;
            }
        }

        if (strs.length == 1) {
            if (m >= zeroes && n >= ones) {
                return 1;
            } else {
                return 0;
            }
        }

        int[][][] dp = new int[strs.length][m + 1][n + 1];
        for (int i = 0; i <= m; ++i) {
            for (int j = 0; j <= n; ++j) {
                if (i >= zeroes && j >= ones) {
                    dp[0][i][j] = 1;
                }
            }
        }

        for (int i = 1; i < strs.length; ++i) {
            zeroes = 0;
            ones = 0;
            for (int idx = 0; idx < strs[i].length(); ++idx) {
                if (strs[i].charAt(idx) == '0') {
                    ++zeroes;
                } else {
                    ++ones;
                }
            }

            for (int j = 0; j <= m; ++j) {
                for (int k = 0; k <= n; ++k) {
                    if (j < zeroes || k < ones) {
                        dp[i][j][k] = dp[i - 1][j][k];
                    } else {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], 1 + dp[i - 1][j - zeroes][k - ones]);
                    }
                }
            }
        }

        return dp[strs.length - 1][m][n];
    }
}
