package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/isomorphic-strings/
 *
 * 给定两个字符串s和t，判断它们是否是同构的。
 * 如果s中的字符可以被替换得到t，那么这两个字符串是同构的。
 * 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
 *
 * 示例 1:
 * 输入: s = "egg", t = "add"
 * 输出: true
 *
 * 示例 2:
 * 输入: s = "foo", t = "bar"
 * 输出: false
 *
 * 示例 3:
 * 输入: s = "paper", t = "title"
 * 输出: true
 *
 * 说明:
 * 你可以假设s和 t 具有相同的长度。
 */
public class Lc205 {

    public static void main(String[] args) {
        System.out.println(new Lc205().isIsomorphic("ab", "aa"));
    }

    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        if (s.isEmpty()) {
            return true;
        }

        int[] si = new int[128];
        int[] ti = new int[128];
        for (int i = 0; i < s.length(); i++) {
            if (si[s.charAt(i)] != ti[t.charAt(i)]) {
                return false;
            } else {
                si[s.charAt(i)] = i + 1;
                ti[t.charAt(i)] = i + 1;
            }
        }

        return true;
    }

    public boolean isIsomorphic1(String s, String t) {
        char[] ch1 = s.toCharArray();
        char[] ch2 = t.toCharArray();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.indexOf(ch1[i]) != t.indexOf(ch2[i])) {
                return false;
            }
        }
        return true;
    }
}
