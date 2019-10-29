package com.diwayou.acm.leetcode.lc500;

/**
 * https://leetcode-cn.com/problems/fibonacci-number/
 */
public class Lc509 {

    public static void main(String[] args) {
        System.out.println(new Lc509().fib(4));
    }

    public int fib(int N) {
        if (N == 0) {
            return 0;
        }
        if (N == 1) {
            return 1;
        }

        int a = 0, b = 1, c;
        int n = N - 2;
        while (n-- >= 0) {
            c = a + b;
            a = b;
            b = c;
        }

        return b;
    }
}
