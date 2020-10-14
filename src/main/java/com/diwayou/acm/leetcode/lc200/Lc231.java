package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/power-of-two/
 *
 * 给定一个整数，编写一个函数来判断它是否是 2 的幂次方。
 *
 * 示例1:
 * 输入: 1
 * 输出: true
 * 解释: 2^0= 1
 *
 * 示例 2:
 * 输入: 16
 * 输出: true
 * 解释: 2^4= 16
 *
 * 示例 3:
 * 输入: 218
 * 输出: false
 */
public class Lc231 {

    public static void main(String[] args) {
        System.out.println(new Lc231().isPowerOfTwo((int) Math.pow(2, 30)));
    }

    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }

        for (int i = 0; i < 31; i++) {
            if ((n | 1 << i) == 1 << i) {
                return true;
            }
        }

        return false;
    }

    public boolean isPowerOfTwo1(int n) {
        return n > 0 && ((n & (n - 1)) == 0);
    }
}
