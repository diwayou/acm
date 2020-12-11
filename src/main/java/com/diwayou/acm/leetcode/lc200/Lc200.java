package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/number-of-islands/
 * <p>
 * 给定一个由'1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
 * <p>
 * 示例 1:
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * 输出:1
 * <p>
 * 示例2:
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * <p>
 * 输出: 3
 */
public class Lc200 {

    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }

        int m = grid.length, n = grid[0].length;

        int cnt = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '0') {
                    continue;
                }

                dfs(grid, i, j, m, n);
                cnt++;
            }
        }

        return cnt;
    }

    private void dfs(char[][] grid, int i, int j, int m, int n) {
        grid[i][j] = '0';

        if (i < m - 1 && grid[i + 1][j] == '1') {
            dfs(grid, i + 1, j, m, n);
        }
        if (j < n - 1 && grid[i][j + 1] == '1') {
            dfs(grid, i, j + 1, m, n);
        }
        if (i > 0 && grid[i - 1][j] == '1') {
            dfs(grid, i - 1, j, m, n);
        }
        if (j > 0 && grid[i][j - 1] == '1') {
            dfs(grid, i, j - 1, m, n);
        }
    }
}
