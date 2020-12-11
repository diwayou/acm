package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/repeated-substring-pattern/
 * <p>
 * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "abab"
 * 输出: True
 * 解释: 可由子字符串 "ab" 重复两次构成。
 * <p>
 * 示例 2:
 * 输入: "aba"
 * 输出: False
 * <p>
 * 示例 3:
 * 输入: "abcabcabcabc"
 * 输出: True
 * 解释: 可由子字符串 "abc" 重复四次构成。 (或者子字符串 "abcabc" 重复两次构成。)
 */
public class Lc459 {

    private boolean check(String s, int i, int j) {
        String str = s.substring(0, i);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < j; index++) {
            sb.append(str);
        }

        return s.equals(sb.toString());
    }

    public boolean repeatedSubstringPattern(String s) {
        int len = s.length();
        if (len == 1) {
            return false;
        }

        for (int i = 1; i <= len / 2; i++) {
            if (len % i == 0 && check(s, i, len / i)) {
                return true;
            }
        }

        return false;
    }

    public boolean repeatedSubstringPattern1(String s) {
        if (null == s) {
            return false;
        }


        int len = s.length();

        for (int i = len / 2; i > 0; i--) {
            if (len % i != 0) {
                continue;
            }

            boolean flag = true;
            for (int j = len / i; j > 0; j--) {
                if (!s.substring(0, i).equals(s.substring((j - 1) * i, j * i))) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return flag;
            }
        }

        return false;
    }
}
