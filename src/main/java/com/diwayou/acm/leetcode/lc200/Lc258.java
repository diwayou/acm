package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/add-digits/
 *
 * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。
 *
 * 示例:
 * 输入: 38
 * 输出: 2
 * 解释: 各位相加的过程为：3 + 8 = 11, 1 + 1 = 2。 由于 2 是一位数，所以返回 2。
 *
 * 进阶:
 * 你可以不使用循环或者递归，且在 O(1) 时间复杂度内解决这个问题吗？
 */
public class Lc258 {

    public static void main(String[] args) {
        System.out.println(new Lc258().addDigits(38));
    }

    public int addDigits(int num) {
        while (num > 9) {
            int t = num;
            num = 0;
            while (t != 0) {
                num += t % 10;
                t /= 10;
            }
        }

        return num;
    }

    public int addDigits1(int num) {
        return (num - 1) % 9 + 1;
    }
}
