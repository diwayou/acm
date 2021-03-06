package com.diwayou.acm.leetcode.lc1200;

/**
 * https://leetcode-cn.com/problems/subtract-the-product-and-sum-of-digits-of-an-integer/
 * <p>
 * 给你一个整数n，请你帮忙计算并返回该整数「各位数字之积」与「各位数字之和」的差。
 * <p>
 * 示例 1：
 * 输入：n = 234
 * 输出：15
 * 解释：
 * 各位数之积 = 2 * 3 * 4 = 24
 * 各位数之和 = 2 + 3 + 4 = 9
 * 结果 = 24 - 9 = 15
 * <p>
 * 示例 2：
 * 输入：n = 4421
 * 输出：21
 * 解释：
 * 各位数之积 = 4 * 4 * 2 * 1 = 32
 * 各位数之和 = 4 + 4 + 2 + 1 = 11
 * 结果 = 32 - 11 = 21
 * <p>
 * 提示：
 * 1 <= n <= 10^5
 */
public class Lc1281 {

    public static void main(String[] args) {
        System.out.println(new Lc1281().subtractProductAndSum(1));
    }

    public int subtractProductAndSum(int n) {
        int m = 1, p = 0;
        while (n > 0) {
            m *= n % 10;
            p += n % 10;

            n /= 10;
        }

        return m - p;
    }
}
