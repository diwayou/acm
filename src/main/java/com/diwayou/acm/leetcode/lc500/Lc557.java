package com.diwayou.acm.leetcode.lc500;

/**
 * https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/
 *
 * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
 *
 * 示例1:
 * 输入: "Let's take LeetCode contest"
 * 输出: "s'teL ekat edoCteeL tsetnoc"
 * 注意：在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。
 */
public class Lc557 {

    public static void main(String[] args) {
        System.out.println(new Lc557().reverseWords("Let's take LeetCode contest"));
    }

    public String reverseWords(String s) {
        int i = 0, j = 0;
        char[] sa = s.toCharArray();
        while (j <= sa.length) {
            if (j == sa.length || sa[j] == ' ') {
                reverse(sa, i, j - 1);

                j++;
                i = j;
            }

            j++;
        }

        return new String(sa);
    }

    private void reverse(char[] sa, int i, int j) {
        while (i < j) {
            char c = sa[i];
            sa[i] = sa[j];
            sa[j] = c;

            i++;
            j--;
        }
    }
}
