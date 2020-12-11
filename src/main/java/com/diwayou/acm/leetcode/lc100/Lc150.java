package com.diwayou.acm.leetcode.lc100;

/**
 * https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/
 * <p>
 * 根据逆波兰表示法，求表达式的值。
 * 有效的运算符包括+,-,*,/。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
 * 说明：
 * 整数除法只保留整数部分。
 * 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
 * <p>
 * 示例1：
 * 输入: ["2", "1", "+", "3", "*"]
 * 输出: 9
 * 解释: ((2 + 1) * 3) = 9
 * <p>
 * 示例2：
 * 输入: ["4", "13", "5", "/", "+"]
 * 输出: 6
 * 解释: (4 + (13 / 5)) = 6
 * <p>
 * 示例3：
 * 输入: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
 * 输出: 22
 * 解释:
 * ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 */
public class Lc150 {

    public static void main(String[] args) {
        System.out.println(new Lc150().evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}));
    }

    public int evalRPN(String[] tokens) {
        int len = tokens.length;
        int[] stack = new int[len / 2 + 1];
        int idx = 0;

        for (String s : tokens) {
            if (s.equals("+")) {
                stack[idx - 2] += stack[idx - 1];
                idx--;
            } else if (s.equals("-")) {
                stack[idx - 2] -= stack[idx - 1];
                idx--;
            } else if (s.equals("*")) {
                stack[idx - 2] *= stack[idx - 1];
                idx--;
            } else if (s.equals("/")) {
                stack[idx - 2] /= stack[idx - 1];
                idx--;
            } else {
                stack[idx++] = Integer.parseInt(s);
            }
        }

        return stack[idx - 1];
    }
}
