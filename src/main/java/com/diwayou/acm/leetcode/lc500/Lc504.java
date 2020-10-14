package com.diwayou.acm.leetcode.lc500;

/**
 * https://leetcode-cn.com/problems/base-7/
 *
 * 给定一个整数，将其转化为7进制，并以字符串形式输出。
 *
 * 示例 1:
 * 输入: 100
 * 输出: "202"
 *
 * 示例 2:
 * 输入: -7
 * 输出: "-10"
 * 注意: 输入范围是[-1e7, 1e7] 。
 */
public class Lc504 {

    public static void main(String[] args) {
        System.out.println(new Lc504().convertToBase7(0));
    }

    public String convertToBase7(int num) {
        if (num == 0) {
            return "0";
        }

        StringBuilder re = new StringBuilder();

        int n = Math.abs(num);
        while (n != 0) {
            re.append(n % 7);
            n /= 7;
        }

        return num < 0 ? "-" + re.reverse().toString() : re.reverse().toString();
    }
}
