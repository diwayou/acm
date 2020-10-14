package com.diwayou.acm.leetcode.lc600;

/**
 * https://leetcode-cn.com/problems/binary-number-with-alternating-bits/
 *
 * 给定一个正整数，检查他是否为交替位二进制数：换句话说，就是他的二进制数相邻的两个位数永不相等。
 *
 * 示例 1:
 * 输入: 5
 * 输出: True
 * 解释:
 * 5的二进制数是: 101
 *
 * 示例 2:
 * 输入: 7
 * 输出: False
 * 解释:
 * 7的二进制数是: 111
 *
 * 示例3:
 * 输入: 11
 * 输出: False
 * 解释:
 * 11的二进制数是: 1011
 *
 * 示例 4:
 * 输入: 10
 * 输出: True
 * 解释:
 * 10的二进制数是: 1010
 */
public class Lc693 {

    public static void main(String[] args) {
        System.out.println(new Lc693().hasAlternatingBits(7));
    }

    public boolean hasAlternatingBits(int n) {
        int prev = n & 1, cur;
        n >>= 1;
        while (n != 0) {
            if ((n & 1) == prev) {
                return false;
            }
            prev = (n & 1);
            n >>= 1;
        }

        return true;
    }

    public boolean hasAlternatingBits1(int n) {
        n = (n ^ (n >> 1));
        return (n & ((long) n + 1)) == 0;
    }
}
