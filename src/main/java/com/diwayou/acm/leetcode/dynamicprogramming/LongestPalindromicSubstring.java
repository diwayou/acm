package com.diwayou.acm.leetcode.dynamicprogramming;

/**
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        System.out.println(new LongestPalindromicSubstring().longestPalindrome1("abacab"));
    }

    public String longestPalindrome(String s) {
        if (s.isEmpty()) {
            return s;
        }

        int[][] dp = new int[s.length()][];
        for (int i = 0; i < dp.length; ++i) {
            dp[i] = new int[s.length()];
        }

        int start = 0, end = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            dp[i][i] = 1;

            for (int j = i + 1; j < s.length(); ++j) {
                if (s.charAt(i) == s.charAt(j) && ((j - i < 2) || dp[i + 1][j - 1] > 0)) {
                    dp[i][j] = 1;
                    if ((j - i) > (end - start)) {
                        start = i;
                        end = j;
                    }
                }
            }
        }

        return s.substring(start, end + 1);
    }

    public String longestPalindrome1(String s) {
        if (s.length() < 2) {
            return s;
        }

        int start = 0, end = 0;
        int left, right;
        for (int i = 0; i < s.length(); ) {
            if (s.length() - i <= (end - start + 1) / 2) {
                break;
            }

            left = right = i;
            while (right < s.length() - 1 && s.charAt(right + 1) == s.charAt(right)) {
                ++right;
            }

            i = right + 1;

            while (left > 0 && right < s.length() - 1 && s.charAt(left - 1) == s.charAt(right + 1)) {
                --left;
                ++right;
            }

            if ((right - left) > (end - start)) {
                start = left;
                end = right;
            }
        }

        return s.substring(start, end + 1);
    }
}
