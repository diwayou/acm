package com.diwayou.acm.leetcode.lc100;

/**
 * https://leetcode-cn.com/problems/reverse-words-in-a-string/
 *
 * 给定一个字符串，逐个翻转字符串中的每个单词。
 *
 * 示例 1：
 * 输入: "the sky is blue"
 * 输出:"blue is sky the"
 *
 * 示例 2：
 * 输入: " hello world! "
 * 输出:"world! hello"
 * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 *
 * 示例 3：
 * 输入: "a good  example"
 * 输出:"example good a"
 * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 * 
 * 说明：
 * 无空格字符构成一个单词。
 * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 * 
 * 进阶：
 * 请选用 C 语言的用户尝试使用O(1) 额外空间复杂度的原地解法。
 */
public class Lc151 {

    public String reverseWords(String s) {
        if (s.isEmpty()) {
            return s;
        }

        String[] sa = s.split(" ");

        StringBuilder re = new StringBuilder(s.length());
        for (int j = sa.length - 1; j >= 0; j--) {
            if (!sa[j].isEmpty()) {
                re.append(sa[j]);
                re.append(' ');
            }
        }

        if (re.length() > 0) {
            re.deleteCharAt(re.length() - 1);
        }

        return re.toString();
    }

    public String reverseWords1(String s) {
        StringBuilder strb = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            if (s.charAt(i) == ' ') {
                i--;
                continue;
            }

            int start = s.lastIndexOf(' ', i);
            strb.append(" ");
            strb.append(s.substring(start + 1, i + 1));
            i = start - 1;
        }
        if (strb.length() > 0) {
            strb.deleteCharAt(0);
        }
        return strb.toString();
    }
}
