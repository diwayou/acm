package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/magic-squares-in-grid/
 *
 * 3 x 3 的幻方是一个填充有从 1 到 9 的不同数字的 3 x 3 矩阵，其中每行，每列以及两条对角线上的各数之和都相等。
 * 给定一个由整数组成的 grid，其中有多少个 3 × 3 的 “幻方” 子矩阵？（每个子矩阵都是连续的）。
 *
 * 示例：
 * 输入: [[4,3,8,4],
 *       [9,5,1,9],
 *       [2,7,6,2]]
 * 输出: 1
 * 解释:
 * 下面的子矩阵是一个 3 x 3 的幻方：
 * 438
 * 951
 * 276
 * 而这一个不是：
 * 384
 * 519
 * 762
 * 总的来说，在本示例所给定的矩阵中只有一个 3 x 3 的幻方子矩阵。
 *
 * 提示:
 * 1 <= grid.length<= 10
 * 1 <= grid[0].length<= 10
 * 0 <= grid[i][j] <= 15
 */
public class Lc840 {

    public static void main(String[] args) {
        int[][] grid = new int[][] {
                {4,3,8,4},
                {9,5,1,9},
                {2,7,6,2}
        };

        System.out.println(new Lc840().numMagicSquaresInside(grid));
    }

    public int numMagicSquaresInside(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if (m < 3 || n < 3) {
            return 0;
        }

        int re = 0;
        for (int i = 0; i <= m - 3; i++) {
            for (int j = 0; j <= n - 3; j++) {
                if (isMs(grid, i, j)) {
                    re++;
                }
            }
        }

        return re;
    }

    private boolean isMs(int[][] grid, int i, int j) {
        int target = grid[i][j] + grid[i + 1][j + 1] + grid[i + 2][j + 2];

        int[] cnt = new int[10];
        for (int r = i; r < i + 3; r++) {
            int t = 0;
            for (int c = j; c < j + 3; c++) {
                if (grid[r][c] < 1 || grid[r][c] > 9) {
                    return false;
                }
                if (cnt[grid[r][c]]++ > 0) {
                    return false;
                }

                t += grid[r][c];
            }

            if (t != target) {
                return false;
            }
        }

        for (int c = j; c < j + 3; c++) {
            int t = 0;
            for (int r = i; r < i + 3; r++) {
                t += grid[r][c];
            }

            if (t != target) {
                return false;
            }
        }

        return target == (grid[i][j + 2] + grid[i + 1][j + 1] + grid[i + 2][j]);
    }

    public int numMagicSquaresInside1(int[][] grid) {
        if (grid.length < 3) return 0;
        int res = 0;
        int R = grid.length - 2, C = grid[0].length - 2;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                //中心点不为5直接跳过
                if (grid[i + 1][j + 1] != 5) continue;
                int[][] temp = {
                        {grid[i][j], grid[i][j + 1], grid[i][j + 2]},
                        {grid[i + 1][j], grid[i + 1][j + 1], grid[i + 1][j + 2]},
                        {grid[i + 2][j], grid[i + 2][j + 1], grid[i + 2][j + 2]}
                };
                //切片
                // int sum =0;
                // for(int k=0;k<3;k++){
                //     for(int l=0;l<3;l++){
                //         temp[k][l] = grid[i+k][j+l];
                //         sum+=temp[k][l];
                //     }
                // }
                // if(sum!=45)continue;
                if (isContain(temp)) {
                    res++;
                    j++;
                }
            }
        }
        return res;
    }

    private boolean isContain(int[][] temp) {
        //四个角为偶数
        if (temp[0][0] % 2 != 0 || temp[2][2] % 2 != 0 || temp[0][2] % 2 != 0 || temp[2][0] % 2 != 0) return false;
        //四个内为奇数
        if (temp[0][1] % 2 == 0 || temp[1][0] % 2 == 0 || temp[1][2] % 2 == 0 || temp[2][1] % 2 == 0) return false;
        //确定中间的横竖和为10
        if (temp[0][1] + temp[2][1] != 10 || temp[1][0] + temp[1][2] != 10) return false;
        if (temp[0][0] + temp[2][2] != 10 || temp[0][2] + temp[2][0] != 10) return false;

        return temp[0][1] + temp[0][2] == temp[1][0] + temp[2][0];
    }
}
