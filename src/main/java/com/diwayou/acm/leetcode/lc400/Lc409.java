package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/longest-palindrome/
 * <p>
 * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
 * 在构造过程中，请注意区分大小写。比如"Aa"不能当做一个回文字符串。
 * <p>
 * 注意:
 * 假设字符串的长度不会超过 1010。
 * <p>
 * 示例 1:
 * 输入:
 * "abccccdd"
 * 输出:
 * 7
 * 解释:
 * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
 */
public class Lc409 {

    public int longestPalindrome(String s) {
        int[] count = new int[128];
        for (char c : s.toCharArray()) {
            count[c]++;
        }

        int sum = 0;
        for (int n : count) {
            sum += n / 2 * 2;
        }

        if (sum < s.length()) {
            sum++;
        }

        return sum;
    }
}
