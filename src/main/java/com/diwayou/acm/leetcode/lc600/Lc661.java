package com.diwayou.acm.leetcode.lc600;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/image-smoother/
 *
 * 包含整数的二维矩阵 M 表示一个图片的灰度。你需要设计一个平滑器来让每一个单元的灰度成为平均灰度 (向下舍入) ，平均灰度的计算是周围的
 * 8个单元和它本身的值求平均，如果周围的单元格不足八个，则尽可能多的利用它们。
 *
 * 示例 1:
 * 输入:
 * [[1,1,1],
 *  [1,0,1],
 *  [1,1,1]]
 * 输出:
 * [[0, 0, 0],
 *  [0, 0, 0],
 *  [0, 0, 0]]
 * 解释:
 * 对于点 (0,0), (0,2), (2,0), (2,2): 平均(3/4) = 平均(0.75) = 0
 * 对于点 (0,1), (1,0), (1,2), (2,1): 平均(5/6) = 平均(0.83333333) = 0
 * 对于点 (1,1): 平均(8/9) = 平均(0.88888889) = 0
 *
 * 注意:
 * 给定矩阵中的整数范围为 [0, 255]。
 * 矩阵的长和宽的范围均为 [1, 150]。
 */
public class Lc661 {

    public static void main(String[] args) {
        int[][] M = {{2,3,4},{5,6,7},{8,9,10},{11,12,13},{14,15,16}};

        System.out.println(Arrays.deepToString(new Lc661().imageSmoother(M)));
        // [[4,4,5],[5,6,6],[8,9,9],[11,12,12],[13,13,14]]
    }

    private static int[][] ds = new int[][]{{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1}};

    public int[][] imageSmoother(int[][] M) {
        int m = M.length, n = M[0].length;
        int[][] re = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = M[i][j];
                int cnt = 1;
                for (int[] d : ds) {
                    int x = d[0] + i;
                    int y = d[1] + j;
                    if (x >= 0 && x < m && y >= 0 && y < n) {
                        v += M[x][y];
                        cnt++;
                    }
                }

                re[i][j] = v / cnt;
            }
        }

        return re;
    }
}
