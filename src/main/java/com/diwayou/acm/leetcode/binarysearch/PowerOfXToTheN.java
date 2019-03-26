package com.diwayou.acm.leetcode.binarysearch;

// Implement pow(x, n).

public class PowerOfXToTheN {
    public static double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }

        if (Double.isInfinite(x)) {
            return 0;
        }

        if (n < 0) {
            n = -n;
            x = 1 / x;
        }

        return n % 2 == 0 ? myPow(x * x, n / 2) : x * myPow(x * x, n / 2);
    }

    public static void main(String[] args) {
        double x = 123;
        int n = 3;
        System.out.println(myPow(x, n));
        System.out.println(Math.pow(x, n));
    }
}
