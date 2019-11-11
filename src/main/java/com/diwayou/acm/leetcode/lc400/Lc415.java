package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/add-strings/
 *
 * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
 *
 * 注意：
 * num1 和num2 的长度都小于 5100.
 * num1 和num2 都只包含数字 0-9.
 * num1 和num2 都不包含任何前导零。
 * 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。
 */
public class Lc415 {

    public static void main(String[] args) {
        System.out.println(new Lc415().addStrings("1", "99"));
    }

    public String addStrings(String num1, String num2) {
        StringBuilder re = new StringBuilder(Math.max(num1.length(), num2.length()) + 1);

        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
        while (i >= 0 || j >= 0) {
            int a = i >= 0 ? num1.charAt(i--) - '0' : 0;
            int b = j >= 0 ? num2.charAt(j--) - '0' : 0;
            int p = a + b + carry;

            re.append(p % 10);
            carry = p / 10;
        }
        if (carry > 0) {
            re.append(carry);
        }

        return re.reverse().toString();
    }
}
