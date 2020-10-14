package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/implement-strstr/
 *
 * 实现strStr()函数。
 * 给定一个haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回 -1。
 *
 * 示例 1:
 * 输入: haystack = "hello", needle = "ll"
 * 输出: 2
 *
 * 示例 2:
 * 输入: haystack = "aaaaa", needle = "bba"
 * 输出: -1
 *
 * 说明:
 * 当needle是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
 * 对于本题而言，当needle是空字符串时我们应当返回 0 。这与C语言的strstr()以及 Java的indexOf()定义相符。
 */
public class Lc28 {

    public static void main(String[] args) {
        System.out.println(new Lc28().strStr("hello", "ll"));
        System.out.println(new Lc28().strStr("hello", "hello"));
        System.out.println(new Lc28().strStr("hello", "o"));
    }

    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }

        if (haystack.isEmpty()) {
            return -1;
        }

        if (needle.length() > haystack.length()) {
            return -1;
        }

        char[] str = haystack.toCharArray();
        char[] s = needle.toCharArray();
        int j;
        for (int i = 0; i < str.length - s.length + 1; i++) {
            for (j = 0; j < s.length; j++) {
                if (str[i + j] != s[j]) {
                    break;
                }
            }

            if (j == s.length) {
                return i;
            }
        }

        return -1;
    }
}
