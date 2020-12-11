package com.diwayou.acm.leetcode.lc1100;

import java.util.Arrays;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/
 * <p>
 * 有效括号字符串 仅由"(" 和")"构成，并符合下述几个条件之一：
 * 空字符串
 * 连接，可以记作AB（A 与 B 连接），其中A和B都是有效括号字符串
 * 嵌套，可以记作(A)，其中A是有效括号字符串
 * 类似地，我们可以定义任意有效括号字符串 s 的 嵌套深度depth(S)：
 * s 为空时，depth("") = 0
 * s 为 A 与 B 连接时，depth(A + B) = max(depth(A), depth(B))，其中A 和B都是有效括号字符串
 * s 为嵌套情况，depth("(" + A + ")") = 1 + depth(A)，其中 A 是有效括号字符串
 * 例如：""，"()()"，和"()(()())"都是有效括号字符串，嵌套深度分别为 0，1，2，而")(" 和"(()"都不是有效括号字符串。
 * <p>
 * 给你一个有效括号字符串 seq，将其分成两个不相交的子序列A 和B，且A 和B满足有效括号字符串的定义（注意：A.length + B.length = seq.length）。
 * 现在，你需要从中选出 任意一组有效括号字符串A 和B，使max(depth(A), depth(B))的可能取值最小。
 * 返回长度为seq.length 答案数组answer，选择A还是B的编码规则是：如果seq[i]是A的一部分，那么answer[i] = 0。否则，
 * answer[i] = 1。即便有多个满足要求的答案存在，你也只需返回一个。
 */
public class Lc1111 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc1111().maxDepthAfterSplit("((())())()")));
    }

    // 这道题我竟然理解的费劲
    public int[] maxDepthAfterSplit(String seq) {
        if (seq == null || seq.isEmpty()) {
            return new int[0];
        }

        int[] result = new int[seq.length()];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == '(') {
                stack.push(i);
            } else {
                int depth = stack.size();
                int left = stack.pop();
                if (depth % 2 == 0) {
                    result[left] = 1;
                    result[i] = 1;
                }
            }
        }

        return result;
    }

    public int[] maxDepthAfterSplit1(String seq) {
        int a = 0, b = 0, N = seq.length();
        int[] ans = new int[N];

        for (int i = 0; i < N; i++) {
            char ch = seq.charAt(i);
            if (ch == '(') {
                if (a > b) {
                    b++;
                    ans[i] = 1;
                } else {
                    a++;
                }
            } else {
                if (a > b) {
                    a--;
                } else {
                    b--;
                    ans[i] = 1;
                }
            }
        }

        return ans;
    }
}
