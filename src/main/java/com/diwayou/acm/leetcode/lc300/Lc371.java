package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/sum-of-two-integers/
 */
public class Lc371 {

    public int getSum(int a, int b) {
        while (b != 0) {
            int res = (a & b) << 1;
            a = a ^ b;
            b = res;
        }

        return a;
    }
}
