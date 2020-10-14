package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/transpose-matrix/
 *
 * 给定一个矩阵A，返回A的转置矩阵。
 * 矩阵的转置是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
 *
 * 示例 1：
 * 输入：[[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[[1,4,7],[2,5,8],[3,6,9]]
 *
 * 示例 2：
 * 输入：[[1,2,3],[4,5,6]]
 * 输出：[[1,4],[2,5],[3,6]]
 * 
 * 提示：
 * 1 <= A.length<= 1000
 * 1 <= A[0].length<= 1000
 */
public class Lc867 {

    public int[][] transpose(int[][] A) {
        int m = A.length, n = A[0].length;

        int[][] re = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                re[i][j] = A[j][i];
            }
        }

        return re;
    }
}
