package com.diwayou.acm.leetcode.lc500;

/**
 * https://leetcode-cn.com/problems/reverse-string-ii/
 *
 * 给定一个字符串和一个整数 k，你需要对从字符串开头算起的每个 2k 个字符的前k个字符进行反转。如果剩余少于 k 个字符，则将剩余的所有全部反转。
 * 如果有小于 2k 但大于或等于 k 个字符，则反转前 k 个字符，并将剩余的字符保持原样。
 *
 * 示例:
 * 输入: s = "abcdefg", k = 2
 * 输出: "bacdfeg"
 *
 * 要求:
 * 该字符串只包含小写的英文字母。
 * 给定字符串的长度和 k 在[1, 10000]范围内。
 */
public class Lc541 {

    public static void main(String[] args) {
        System.out.println(new Lc541().reverseStr("abcdefg", 2));
        System.out.println(new Lc541().reverseStr("abcdefgh", 3));
    }

    public String reverseStr(String s, int k) {
        if (s.length() == 1 || k == 1) {
            return s;
        }

        char[] sa = s.toCharArray();

        for (int i = 0; i < sa.length; i += 2 * k) {
            reverseString(sa, i, (i + k - 1 < sa.length) ? (i + k - 1) : sa.length - 1);
        }

        return new String(sa);
    }

    public void reverseString(char[] s, int i, int j) {
        char c;
        while (i < j) {
            c = s[i];
            s[i] = s[j];
            s[j] = c;

            i++;
            j--;
        }
    }
}
