package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 * <p>
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * <p>
 * 示例 1：
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * <p>
 * 示例 2：
 * 输入: "cbbd"
 * 输出: "bb"
 */
public class Lc5 {

    public static void main(String[] args) {
        System.out.println(new Lc5().longestPalindrome("babad"));
        System.out.println(new Lc5().longestPalindrome("cbbd"));
        System.out.println(new Lc5().longestPalindrome("bb"));
        System.out.println(new Lc5().longestPalindrome("ab"));
        System.out.println(new Lc5().longestPalindrome("aba"));
    }

    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        char[] c = s.toCharArray();
        int[] r = new int[2];
        for (int i = 0; i < c.length; i++) {
            i = longest(i, r, c);
        }

        return s.substring(r[0], r[1] + 1);
    }

    public int longest(int low, int[] r, char[] c) {
        int high = low;
        while (high < c.length - 1 && c[low] == c[high + 1]) {
            ++high;
        }
        int ans = high;
        while (low > 0 && high < c.length - 1 && c[low - 1] == c[high + 1]) {
            --low;
            ++high;
        }
        if (high - low > r[1] - r[0]) {
            r[0] = low;
            r[1] = high;
        }

        return ans;
    }
}
