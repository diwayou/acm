package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/generate-parentheses/
 *
 * 给出n代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 * 例如，给出n = 3，生成结果为：
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 */
public class Lc22 {

    public static void main(String[] args) {
        System.out.println(new Lc22().generateParenthesis(3));
    }

    public List<String> generateParenthesis(int n) {
        List<String> re = new ArrayList<>();
        char[] backtrack = new char[n * 2];

        generateParenthesisHelper(n, n, backtrack, 0, re);

        return re;
    }

    private void generateParenthesisHelper(int l, int r, char[] backtrack, int idx, List<String> re) {
        if (l == 0 && r == 0) {
            re.add(new String(backtrack));
            return;
        }

        if (l > 0) {
            backtrack[idx] = '(';
            generateParenthesisHelper(l - 1, r, backtrack, idx + 1, re);
        }
        if (r > 0 && r > l) {
            backtrack[idx] = ')';
            generateParenthesisHelper(l, r - 1, backtrack, idx + 1, re);
        }
    }
}
