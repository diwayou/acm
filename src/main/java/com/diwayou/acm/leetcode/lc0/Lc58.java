package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/length-of-last-word/
 *
 * 给定一个仅包含大小写字母和空格 ' ' 的字符串，返回其最后一个单词的长度。
 * 如果不存在最后一个单词，请返回 0 。
 * 说明：一个单词是指由字母组成，但不包含任何空格的字符串。
 * 示例:
 * 输入: "Hello World"
 * 输出: 5
 */
public class Lc58 {

    public static void main(String[] args) {
        System.out.println(new Lc58().lengthOfLastWord(""));
        System.out.println(new Lc58().lengthOfLastWord(" "));
        System.out.println(new Lc58().lengthOfLastWord("abc "));
        System.out.println(new Lc58().lengthOfLastWord("hello world"));
    }

    public int lengthOfLastWord(String s) {
        if (s.isEmpty()) {
            return 0;
        }

        int cnt = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                if (cnt > 0) {
                    break;
                }
            } else {
                cnt++;
            }
        }

        return cnt;
    }
}
