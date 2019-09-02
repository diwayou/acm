package com.diwayou.acm.leetcode.dynamicprogramming;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/longest-palindromic-subsequence/
 *
 */
public class LongestPalindromicSubsequence {

    public static void main(String[] args) {
        System.out.println(new LongestPalindromicSubsequence().longestPalindromeSubseq1("aaaa"));
    }

    /**
     * 其中i和j表示字符串的区间开始结束[i,j]之间的字符串最大回文长度值
     *
     * dp[i][j] = dp[i + 1][j - 1] + 2 if (s[i] == s[j])
     * dp[i][j] = max(dp[i + 1][j], dp[i][j - 1]) if (s[i] != s[j])
     */
    public int longestPalindromeSubseq(String s) {
        int[][] dp = new int[s.length()][];
        for (int i = 0; i < s.length(); ++i) {
            dp[i] = new int[s.length()];
        }

        for (int i = s.length() - 1; i >= 0; --i) {
            dp[i][i] = 1;

            for (int j = i + 1; j < s.length(); ++j) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][s.length() - 1];
    }

    /**
     * j表示当前到[i+1,j]的当前最长的回文长度
     * dp[j] = dp[j] + 2 if (s[i] == s[j])
     */
    public int longestPalindromeSubseq1(String s) {
        int[] dp = new int[s.length()];
        Arrays.fill(dp, 1);

        int len;
        int t;
        for (int i = s.length() - 1; i >= 0; --i) {
            len = 0;

            for (int j = i + 1; j < s.length(); ++j) {
                t = dp[j];
                if (s.charAt(i) == s.charAt(j)) {
                    dp[j] = len + 2;
                }
                len = Math.max(len, t);
            }
        }

        int re = 0;
        for (int i = 0; i < dp.length; ++i) {
            re = Math.max(re, dp[i]);
        }

        return re;
    }
}
