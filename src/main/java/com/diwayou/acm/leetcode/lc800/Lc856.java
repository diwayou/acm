package com.diwayou.acm.leetcode.lc800;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/score-of-parentheses/
 * <p>
 * 给定一个平衡括号字符串S，按下述规则计算该字符串的分数：
 * () 得 1 分。
 * AB 得A + B分，其中 A 和 B 是平衡括号字符串。
 * (A) 得2 * A分，其中 A 是平衡括号字符串。
 * <p>
 * 示例 1：
 * 输入： "()"
 * 输出： 1
 * <p>
 * 示例 2：
 * 输入： "(())"
 * 输出： 2
 * <p>
 * 示例3：
 * 输入： "()()"
 * 输出： 2
 * <p>
 * 示例4：
 * 输入： "(()(()))"
 * 输出： 6
 * <p>
 * 提示：
 * S是平衡括号字符串，且只含有(和)。
 * 2 <= S.length <= 50
 */
public class Lc856 {

    public int scoreOfParentheses1(String S) {
        int re = 0, bal = 0;
        for (int i = 0; i < S.length(); ++i) {
            if (S.charAt(i) == '(') {
                bal++;
            } else {
                bal--;
                if (S.charAt(i - 1) == '(')
                    re += 1 << bal;
            }
        }

        return re;
    }

    public int scoreOfParentheses2(String S) {
        Stack<Integer> stack = new Stack();
        stack.push(0); // The score of the current frame

        for (char c : S.toCharArray()) {
            if (c == '(')
                stack.push(0);
            else {
                int v = stack.pop();
                int w = stack.pop();
                stack.push(w + Math.max(2 * v, 1));
            }
        }

        return stack.pop();
    }

    public int scoreOfParentheses(String S) {
        return F(S, 0, S.length());
    }

    public int F(String S, int i, int j) {
        //Score of balanced string S[i:j]
        int ans = 0, bal = 0;

        // Split string into primitives
        for (int k = i; k < j; ++k) {
            bal += S.charAt(k) == '(' ? 1 : -1;
            if (bal == 0) {
                if (k - i == 1) ans++;
                else ans += 2 * F(S, i + 1, k);
                i = k + 1;
            }
        }

        return ans;
    }
}
