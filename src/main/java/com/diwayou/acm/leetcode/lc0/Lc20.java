package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/valid-parentheses/
 */
public class Lc20 {

    public boolean isValid(String s) {
        if (s.isEmpty()) {
            return true;
        }

        char[] stack = new char[s.length()];
        int idx = 0;
        char ch, top;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (ch == ' ') {
                continue;
            }

            if (idx == 0 || ch == '(' || ch == '{' || ch == '[') {
                stack[idx++] = s.charAt(i);
                continue;
            }

            top = stack[idx - 1];
            if ((ch == ')' && top == '(') ||
                    (ch == '}' && top == '{') ||
                    (ch == ']' && top == '[')) {
                idx--;
            } else {
                return false;
            }
        }

        return idx == 0;
    }
}
