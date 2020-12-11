package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/binary-gap/
 * <p>
 * 给定一个正整数 N，找到并返回 N 的二进制表示中两个连续的 1 之间的最长距离。
 * <p>
 * 如果没有两个连续的 1，返回 0 。
 */
public class Lc868 {

    public static void main(String[] args) {
        System.out.println(new Lc868().binaryGap(22));
    }

    public int binaryGap(int N) {
        if (N <= 2) {
            return 0;
        }

        int last = -1;
        int cur = 0;
        int max = 0;
        while (N > 0) {
            if ((N & 1) == 1) {
                if (last >= 0) {
                    max = Math.max(max, cur - last);
                }

                last = cur;
            }
            cur++;
            N >>= 1;
        }

        return max;
    }
}
