package com.diwayou.acm.leetcode.lc100;

/**
 * https://leetcode-cn.com/problems/valid-palindrome/
 *
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 *
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * 示例 1:
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 *
 * 示例 2:
 * 输入: "race a car"
 * 输出: false
 */
public class Lc125 {

    public static void main(String[] args) {
        System.out.println(new Lc125().isPalindrome("A man, a plan, a canal: Panama"));
    }

    public boolean isPalindrome(String s) {
        char[] sa = s.toCharArray();
        int i = 0, j = sa.length - 1;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(sa[i])) {
                i++;
            }
            while (j > i && !Character.isLetterOrDigit(sa[j])) {
                j--;
            }

            if (i >= j) {
                break;
            }

            if (Character.toLowerCase(sa[i]) != Character.toLowerCase(sa[j])) {
                return false;
            }

            i++;
            j--;
        }

        return true;
    }
}
