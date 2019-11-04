package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/rotting-oranges/
 *
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 *
 * 返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。

 * 示例 1：
 * 输入：[[2,1,1],[1,1,0],[0,1,1]]
 * 输出：4
 *
 * 示例 2：
 * 输入：[[2,1,1],[0,1,1],[1,0,1]]
 * 输出：-1
 * 解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个正向上。
 *
 * 示例 3：
 * 输入：[[0,2]]
 * 输出：0
 * 解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
 *  
 * 提示：
 * 1 <= grid.length <= 10
 * 1 <= grid[0].length <= 10
 * grid[i][j] 仅为 0、1 或 2
 */
public class Lc994 {

    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {2,1,1},
                {0,1,1},
                {1,0,1}
        };

        System.out.println(new Lc994().orangesRotting(grid));
    }

    private int m;

    private int n;

    public int orangesRotting(int[][] grid) {
        m = grid.length;
        n = grid[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    grid[i][j] = 1;

                    dfs(grid, i, j, 0);
                }
            }
        }

        int re = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }

                if (grid[i][j] < re) {
                    re = grid[i][j];
                }
            }
        }

        return re == 0 ? 0 : -re;
    }

    private void dfs(int[][] grid, int i, int j, int d) {
        int v = grid[i][j];
        if (v == 2 || v == 0) {
            return;
        }

        if (v < d || v == 1) {
            grid[i][j] = d;
        } else {
            return;
        }

        if (i > 0 && (grid[i - 1][j] < 0 || grid[i - 1][j] == 1)) {
            dfs(grid, i - 1, j, d - 1);
        }
        if (i < m - 1 && (grid[i + 1][j] < 0 || grid[i + 1][j] == 1)) {
            dfs(grid, i + 1, j, d - 1);
        }
        if (j > 0 && (grid[i][j - 1] < 0 || grid[i][j - 1] == 1)) {
            dfs(grid, i, j - 1, d - 1);
        }
        if (j < n - 1 && (grid[i][j + 1] < 0 || grid[i][j + 1] == 1)) {
            dfs(grid, i, j + 1, d - 1);
        }
    }
}
