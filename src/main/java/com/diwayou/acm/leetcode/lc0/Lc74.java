package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/search-a-2d-matrix/
 *
 * 编写一个高效的算法来判断m x n矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 */
public class Lc74 {

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) {
            return false;
        }
        int n = matrix[0].length;
        if (n == 0) {
            return false;
        }

        int top = 0, bottom = m - 1;
        int rmid, cmid;
        int left = 0, right = n - 1;
        while (top <= bottom) {
            rmid = (top + bottom) / 2;
            if (target > matrix[rmid][n - 1]) {
                top = top + 1;
            } else if (target < matrix[rmid][0]) {
                bottom = bottom - 1;
            } else {
                while (left <= right) {
                    cmid = (left + right) / 2;
                    if (target > matrix[rmid][cmid]) {
                        left = left + 1;
                    } else if (target < matrix[rmid][cmid]) {
                        right = right - 1;
                    } else {
                        return true;
                    }
                }

                return false;
            }
        }

        return false;
    }
}
