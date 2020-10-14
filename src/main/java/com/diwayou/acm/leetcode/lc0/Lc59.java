package com.diwayou.acm.leetcode.lc0;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/spiral-matrix-ii/
 *
 * 给定一个正整数n，生成一个包含 1 到n^2所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。
 *
 * 示例:
 * 输入: 3
 * 输出:
 * [
 *  [ 1, 2, 3 ],
 *  [ 8, 9, 4 ],
 *  [ 7, 6, 5 ]
 * ]
 */
public class Lc59 {

    public static void main(String[] args) {
        int[][] re = new Lc59().generateMatrix(6);
        for (int i = 0; i < re.length; i++) {
            System.out.println(Arrays.toString(re[i]));
        }
    }

    public int[][] generateMatrix(int n) {
        int[][] re = new int[n][n];

        int r1 = 0, r2 = n - 1;
        int c1 = 0, c2 = n - 1;

        int num = 1;
        while (r1 <= r2 && c1 <= c2) {
            for (int c = c1; c <= c2; c++) {
                re[r1][c] = num++;
            }
            for (int r = r1 + 1; r <= r2; r++) {
                re[r][c2] = num++;
            }
            if (r1 < r2 && c1 < c2) {
                for (int c = c2 - 1; c > c1; c--) {
                    re[r2][c] = num++;
                }
                for (int r = r2; r > r1; r--) {
                    re[r][c1] = num++;
                }
            }

            r1++;
            r2--;
            c1++;
            c2--;
        }

        return re;
    }
}
