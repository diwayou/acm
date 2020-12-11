package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/reverse-vowels-of-a-string/
 * <p>
 * 编写一个函数，以字符串作为输入，反转该字符串中的元音字母。
 * <p>
 * 示例 1:
 * 输入: "hello"
 * 输出: "holle"
 * <p>
 * 示例 2:
 * 输入: "leetcode"
 * 输出: "leotcede"
 * <p>
 * 说明:
 * 元音字母不包含字母"y"。
 */
public class Lc345 {

    public static void main(String[] args) {
        System.out.println(new Lc345().reverseVowels("OE"));
    }

    public String reverseVowels(String s) {
        char[] sa = s.toCharArray();
        int i = 0, j = sa.length - 1;
        while (i < j) {
            while (i < sa.length && !isVowel(sa[i])) {
                i++;
            }
            while (j >= 0 && !isVowel(sa[j])) {
                j--;
            }

            if (i >= j) {
                break;
            }

            char c = sa[i];
            sa[i] = sa[j];
            sa[j] = c;

            i++;
            j--;
        }

        return new String(sa);
    }

    private static boolean isVowel(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
            return true;
        }

        return false;
    }
}
