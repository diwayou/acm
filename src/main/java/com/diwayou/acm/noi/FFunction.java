package com.diwayou.acm.noi;

/**
 * http://oj.noi.cn/oj/#main/show/1075
 */
public class FFunction {
    public static void main(String[] args) {
        System.out.printf("%.2f", f(4.2, 10));
    }

    private static double f(double x, int n) {

        int step = 1;
        double result = x;
        while (step <= n) {
            result = Math.sqrt(step + result);
            step++;
        }

        return result;
    }
}
