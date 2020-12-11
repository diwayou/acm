package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/happy-number/
 * <p>
 * 编写一个算法来判断一个数是不是“快乐数”。
 * 一个“快乐数”定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是无限循环
 * 但始终变不到 1。如果可以变为 1，那么这个数就是快乐数。
 * <p>
 * 示例:
 * 输入: 19
 * 输出: true
 * 解释:
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 */
public class Lc202 {

    public static void main(String[] args) {
        System.out.println(new Lc202().isHappy(1589));
    }

    public boolean isHappy(int n) {
        int t = n, r, p;
        do {
            r = 0;
            while (t != 0) {
                p = t % 10;
                r += p * p;
                t /= 10;
            }
            t = r;
            if (t == 1) {
                return true;
            }
        } while (t != 4);

        return false;
    }
}
