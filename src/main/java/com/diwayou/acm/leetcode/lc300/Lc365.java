package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/water-and-jug-problem/
 * <p>
 * 有两个容量分别为x升 和 y升 的水壶以及无限多的水。请判断能否通过使用这两个水壶，从而可以得到恰好z升 的水？
 * <p>
 * 如果可以，最后请用以上水壶中的一或两个来盛放取得的z升水。
 * <p>
 * 你允许：
 * <p>
 * 装满任意一个水壶
 * 清空任意一个水壶
 * 从一个水壶向另外一个水壶倒水，直到装满或者倒空
 * 示例 1: (From the famous "Die Hard" example)
 * <p>
 * 输入: x = 3, y = 5, z = 4
 * 输出: True
 * 示例 2:
 * <p>
 * 输入: x = 2, y = 6, z = 5
 * 输出: False
 */
public class Lc365 {

    public static void main(String[] args) {
        System.out.println(new Lc365().canMeasureWater(3, 5, 4));
        System.out.println(new Lc365().canMeasureWater(2, 6, 5));
    }

    public boolean canMeasureWater(int x, int y, int z) {
        if (z == x || z == y || z == x + y || z == 0) {
            return true;
        }

        if (z > x + y) {
            return false;
        }

        int r = gcd(x, y);

        return z % r == 0;
    }

    private static int gcd(int a, int b) {
        int r;
        while (b != 0) {
            r = a % b;
            a = b;
            b = r;
        }

        return a;
    }
}
