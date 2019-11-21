package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/hamming-distance/
 *
 * 两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。
 * 给出两个整数 x 和 y，计算它们之间的汉明距离。
 * 注意：
 * 0 ≤ x, y < 2^31.
 *
 * 示例:
 * 输入: x = 1, y = 4
 * 输出: 2
 * 解释:
 * 1   (0 0 0 1)
 * 4   (0 1 0 0)
 *        ↑   ↑
 * 上面的箭头指出了对应二进制位不同的位置。
 */
public class Lc461 {

    public int hammingDistance(int x, int y) {
        int re = 0;
        while (x != 0 || y != 0) {
            if ((x & 1) != (y & 1)) {
                re++;
            }

            x >>= 1;
            y >>= 1;
        }

        return re;
    }

    public int hammingDistance1(int x, int y) {
        int num = x ^ y;
        int re = 0;

        while (num != 0) {
            re++;
            num &= (num-1);
        }

        return re;
    }
}
