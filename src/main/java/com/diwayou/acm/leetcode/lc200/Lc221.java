package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/maximal-square/
 * <p>
 * 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
 * <p>
 * 示例:
 * 输入:
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 * 输出: 4
 */
public class Lc221 {

    public int maximalSquare(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int maxsqlen = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != '1') {
                    continue;
                }

                int sqlen = 1;
                boolean flag = true;
                while (sqlen + i < rows && sqlen + j < cols && flag) {
                    for (int k = j; k <= sqlen + j; k++) {
                        if (matrix[i + sqlen][k] == '0') {
                            flag = false;
                            break;
                        }
                    }
                    for (int k = i; k <= sqlen + i; k++) {
                        if (matrix[k][j + sqlen] == '0') {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        sqlen++;
                }

                if (maxsqlen < sqlen) {
                    maxsqlen = sqlen;
                }
            }
        }

        return maxsqlen * maxsqlen;
    }

    public int maximalSquare1(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;

        int[][] dp = new int[rows + 1][cols + 1];
        int maxsqlen = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[i][j]);
                }
            }
        }

        return maxsqlen * maxsqlen;
    }

    public int maximalSquare2(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;

        int[] dp = new int[cols + 1];
        int maxsqlen = 0, prev = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], dp[j]), prev) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }

        return maxsqlen * maxsqlen;
    }
}
