package com.diwayou.acm.leetcode.lc600;

/**
 * https://leetcode-cn.com/problems/valid-palindrome-ii/
 * <p>
 * 给定一个非空字符串s，最多删除一个字符。判断是否能成为回文字符串。
 * <p>
 * 示例 1:
 * 输入: "aba"
 * 输出: True
 * <p>
 * 示例 2:
 * 输入: "abca"
 * 输出: True
 * 解释: 你可以删除c字符。
 * <p>
 * 注意:
 * 字符串只包含从 a-z 的小写字母。字符串的最大长度是50000。
 */
public class Lc680 {

    public static void main(String[] args) {
        System.out.println(new Lc680().validPalindrome("aba"));
    }

    public boolean validPalindrome(String s) {
        char[] sa = s.toCharArray();

        return validPalindromeHelper(sa, 0, sa.length - 1, true);
    }

    private boolean validPalindromeHelper(char[] sa, int i, int j, boolean can) {
        while (i < j) {
            if (sa[i] != sa[j]) {
                if (can) {
                    return validPalindromeHelper(sa, i + 1, j, false) || validPalindromeHelper(sa, i, j - 1, false);
                } else {
                    return false;
                }
            }

            i++;
            j--;
        }

        return true;
    }
}
