package com.diwayou.acm.leetcode.lc200;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/different-ways-to-add-parentheses/
 * <p>
 * 给定一个含有数字和运算符的字符串，为表达式添加括号，改变其运算优先级以求出不同的结果。你需要给出所有可能的组合的结果。
 * 有效的运算符号包含+,-以及*。
 * <p>
 * 示例1:
 * 输入: "2-1-1"
 * 输出: [0, 2]
 * 解释:
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 * <p>
 * 示例2:
 * 输入: "2*3-4*5"
 * 输出: [-34, -14, -10, -10, 10]
 * 解释:
 * (2*(3-(4*5))) = -34
 * ((2*3)-(4*5)) = -14
 * ((2*(3-4))*5) = -10
 * (2*((3-4)*5)) = -10
 * (((2*3)-4)*5) = 10
 */
public class Lc241 {

    public static void main(String[] args) {
        System.out.println(new Lc241().diffWaysToCompute("2-1-1"));
        System.out.println(new Lc241().diffWaysToCompute("2*3-4*5"));
    }

    public List<Integer> diffWaysToCompute1(String input) {
        List<Integer> result = new ArrayList<>();
        if (!input.contains("+") && !input.contains("-") && !input.contains("*")) {
            result.add(Integer.valueOf(input));
            return result;
        }

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*') {
                for (Integer left : diffWaysToCompute(input.substring(0, i))) {
                    for (Integer right : diffWaysToCompute(input.substring(i + 1))) {
                        if (input.charAt(i) == '+') {
                            result.add(left + right);
                        } else if (input.charAt(i) == '-') {
                            result.add(left - right);
                        } else if (input.charAt(i) == '*') {
                            result.add(left * right);
                        }
                    }
                }
            }
        }

        return result;
    }

    Map<String, List<Integer>> map = new HashMap<>();

    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> result = new ArrayList<>();
        if (input == null || input.length() == 0) {
            return result;
        }

        if (map.containsKey(input)) {
            return map.get(input);
        }

        if (isDigit(input)) {
            result.add(Integer.valueOf(input));
            map.put(input, result);
            return result;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                String a = input.substring(0, i);
                char operator = input.charAt(i);
                String b = input.substring(i + 1);
                List<Integer> ar = diffWaysToCompute(a);
                List<Integer> br = diffWaysToCompute(b);

                for (int x : ar) {
                    for (int y : br) {
                        result.add(calculate(x, y, operator));
                    }
                }
            }
        }

        map.put(input, result);

        return result;
    }

    private boolean isDigit(String input) {
        return input != null && input.indexOf('+') == -1 && input.indexOf('-') == -1 && input.indexOf('*') == -1;
    }

    private int calculate(int a, int b, int operator) {
        if (operator == '+') {
            return a + b;
        }

        if (operator == '-') {
            return a - b;
        }

        return a * b;
    }
}
