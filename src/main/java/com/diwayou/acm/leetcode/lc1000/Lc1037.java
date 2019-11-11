package com.diwayou.acm.leetcode.lc1000;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/valid-boomerang/
 *
 * 回旋镖定义为一组三个点，这些点各不相同且不在一条直线上。
 * 给出平面上三个点组成的列表，判断这些点是否可以构成回旋镖。
 *
 * 示例 1：
 * 输入：[[1,1],[2,3],[3,2]]
 * 输出：true
 *
 * 示例 2：
 * 输入：[[1,1],[2,2],[3,3]]
 * 输出：false
 *
 * 提示：
 * points.length == 3
 * points[i].length == 2
 * 0 <= points[i][j] <= 100
 */
public class Lc1037 {

    public static void main(String[] args) {
        System.out.println(new Lc1037().isBoomerang(new int[][]{{1,2},{0,1},{0,0}}));
        System.out.println(new Lc1037().isBoomerang(new int[][]{{1,0},{0,0},{2,0}}));
        System.out.println(new Lc1037().isBoomerang(new int[][]{{0, 1},{2,1},{0,0}}));
        System.out.println(new Lc1037().isBoomerang(new int[][]{{1,1},{2,3},{3,2}}));
        System.out.println(new Lc1037().isBoomerang(new int[][]{{1,1},{2,2},{3,3}}));
        System.out.println(new Lc1037().isBoomerang(new int[][]{{0, 0},{2,1},{2,1}}));
    }

    public boolean isBoomerang(int[][] points) {
        // y = ax + b
        // y1 = ax1 + b
        // y2 = ax2 + b
        // a(x1 - x2) = y1 - y2
        // a = (y1 - y2) / (x1 - x2) if x1 != x2
        // b = y1 - ax1
        if (Arrays.equals(points[0], points[1]) || Arrays.equals(points[0], points[2]) || Arrays.equals(points[1], points[2])) {
            return false;
        }

        return !Arrays.equals(resolveCoefficient(points[0], points[1]), resolveCoefficient(points[1], points[2]));
    }

    private static float[] resolveCoefficient(int[] x1y1, int[] x2y2) {
        if (x1y1[0] == x2y2[0]) {
            return new float[]{1, x1y1[0] == 0 ? 0 : x1y1[1] - x2y2[1]};
        }

        float a = x1y1[1] == x2y2[1] ? 0 : (x1y1[1] - x2y2[1]) / (float)(x1y1[0] - x2y2[0]);

        return new float[]{a, x1y1[1] - a * x1y1[0]};
    }

    public boolean isBoomerang1(int[][] points) {
        if (points[0][0] == points[1][0] && points[1][0] == points[2][0] && points[0][1] == points[1][1] && points[1][1] == points[2][1]) {
            return false;
        }

        int x1 = points[0][0] - points[1][0];
        int x2 = points[0][0] - points[2][0];

        int y1 = points[0][1] - points[1][1];
        int y2 = points[0][1] - points[2][1];

        return x1 * y2 - x2 * y1 != 0;
    }
}
