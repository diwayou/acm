package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/backspace-string-compare/
 * <p>
 * 给定 S 和 T 两个字符串，当它们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。 # 代表退格字符。
 */
public class Lc844 {

    public static void main(String[] args) {
        System.out.println(new Lc844().backspaceCompare("y#fo##f", "y#f#o##f"));
    }

    public boolean backspaceCompare(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();

        int sLen = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '#') {
                if (sLen > 0) {
                    sLen--;
                }
            } else {
                s[sLen++] = s[i];
            }
        }

        int tLen = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] == '#') {
                if (tLen > 0) {
                    tLen--;
                }
            } else {
                t[tLen++] = t[i];
            }
        }

        if (sLen != tLen) {
            return false;
        }

        for (int i = 0; i < sLen; i++) {
            if (s[i] != t[i]) {
                return false;
            }
        }

        return true;
    }
}