package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/powx-n/
 * <p>
 * -100.0 < x < 100.0
 * n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1]
 */
public class Lc50 {

    public static void main(String[] args) {
        System.out.println(new Lc50().myPow(0.00001, 2147483647));
    }

    public double myPow(double x, int n) {
        if (x == 0) {
            return x;
        }

        int nn = n >= 0 ? n : -n;

        double re = doPow(x, nn);

        if (n < 0) {
            re = 1 / re;
        }

        return re;
    }

    public double doPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }

        double half = doPow(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }

    public double myPow1(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        double ans = 1;
        double cur = x;
        for (long i = N; i > 0; i /= 2) {
            if ((i % 2) == 1) {
                ans = ans * cur;
            }
            cur = cur * cur;
        }

        return ans;
    }
}
