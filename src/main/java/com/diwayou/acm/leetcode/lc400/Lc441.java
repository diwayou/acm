package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/arranging-coins/
 * <p>
 * 你总共有n枚硬币，你需要将它们摆成一个阶梯形状，第k行就必须正好有k枚硬币。
 * <p>
 * 给定一个数字n，找出可形成完整阶梯行的总行数。
 * <p>
 * n是一个非负整数，并且在32位有符号整型的范围内
 */
public class Lc441 {
    public static void main(String[] args) {
        System.out.println(new Lc441().arrangeCoins(1804289383));
    }

    public int arrangeCoins(int n) {
        if (n <= 1) {
            return n;
        }

        return (int) (Math.sqrt(2 * (long) n + 0.25) - 0.5);
    }

    public int arrangeCoins1(int n) {
        if (n <= 1) {
            return n;
        }

        long sum = 1;
        int i = 1;
        while (sum <= n) {
            sum += ++i;
        }

        return i - 1;
    }
}
