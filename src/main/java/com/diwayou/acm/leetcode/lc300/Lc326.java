package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/power-of-three/
 * <p>
 * 给定一个整数，写一个函数来判断它是否是 3的幂次方。
 * <p>
 * 示例 1:
 * 输入: 27
 * 输出: true
 * <p>
 * 示例 2:
 * 输入: 0
 * 输出: false
 * <p>
 * 示例 3:
 * 输入: 9
 * 输出: true
 * <p>
 * 示例 4:
 * 输入: 45
 * 输出: false
 * <p>
 * 进阶：
 * 你能不使用循环或者递归来完成本题吗？
 */
public class Lc326 {

    public boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }

    public boolean isPowerOfThree1(int n) {
        if (n < 1) {
            return false;
        }

        while (n % 3 == 0) {
            n /= 3;
        }

        return n == 1;
    }
}
