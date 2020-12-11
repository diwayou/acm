package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/find-the-difference/
 * <p>
 * 给定两个字符串 s 和 t，它们只包含小写字母。
 * 字符串t由字符串s随机重排，然后在随机位置添加一个字母。
 * 请找出在 t 中被添加的字母。
 * <p>
 * 示例:
 * 输入：
 * s = "abcd"
 * t = "abcde"
 * 输出：
 * e
 * 解释：
 * 'e' 是那个被添加的字母。
 */
public class Lc389 {

    public char findTheDifference(String s, String t) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            if (--cnt[c - 'a'] < 0) {
                return c;
            }
        }

        return ' ';
    }

    public char findTheDifference1(String s, String t) {
        int a = 0, b = 0, len = s.length();
        for (int i = 0; i < len; i++) {
            a += s.charAt(i);
            b += t.charAt(i);
        }

        return (char) (b + t.charAt(len) - a);
    }

    public char findTheDifference2(String s, String t) {
        char result = t.charAt(t.length() - 1);
        for (int i = 0; i < s.length(); i++) {
            result ^= s.charAt(i);
            result ^= t.charAt(i);
        }

        return result;
    }
}
