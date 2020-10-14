package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/minimum-add-to-make-parentheses-valid/
 *
 * 给定一个由'('和')'括号组成的字符串 S，我们需要添加最少的括号（ '('或是')'，可以在任何位置），以使得到的括号字符串有效。
 * 从形式上讲，只有满足下面几点之一，括号字符串才是有效的：
 * 它是一个空字符串，或者
 * 它可以被写成AB（A与B连接）, 其中A 和B都是有效字符串，或者
 * 它可以被写作(A)，其中A是有效字符串。
 * 给定一个括号字符串，返回为使结果字符串有效而必须添加的最少括号数。
 *
 * 示例 1：
 * 输入："())"
 * 输出：1
 *
 * 示例 2：
 * 输入："((("
 * 输出：3
 *
 * 示例 3：
 * 输入："()"
 * 输出：0
 *
 * 示例 4：
 * 输入："()))(("
 * 输出：4
 * 
 * 提示：
 * S.length <= 1000
 * S 只包含'(' 和')'字符。
 */
public class Lc921 {

    public static void main(String[] args) {
        System.out.println(new Lc921().minAddToMakeValid("()))(("));
    }

    public int minAddToMakeValid(String S) {
        int l = 0, r = 0;
        for (char c : S.toCharArray()) {
            if (c == '(') {
                l++;
            } else if (c == ')') {
                l--;
                if (l < 0) {
                    r++;
                    l = 0;
                }
            }
        }

        return l + r;
    }
}
