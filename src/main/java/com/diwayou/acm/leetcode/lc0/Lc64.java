package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/minimum-path-sum/
 *
 * 给定一个包含非负整数的 mxn网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 *
 * 示例:
 * 输入:
 * [
 *  [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 */
public class Lc64 {

    private int[][] dist;

    private int m;

    private int n;

    public int minPathSum2(int[][] grid) {
        m = grid.length;
        if (m == 0) {
            return 0;
        }

        n = grid[0].length;
        if (n == 0) {
            return 0;
        }

        dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        dfs(grid, 0, 0, 0);

        return dist[m - 1][n - 1];
    }

    private void dfs(int[][] grid, int i, int j, int cur) {
        if (grid[i][j] + cur >= dist[i][j]) {
            return;
        }

        dist[i][j] = grid[i][j] + cur;

        if (i < m - 1) {
            dfs(grid, i + 1, j, dist[i][j]);
        }
        if (j < n - 1) {
            dfs(grid, i, j + 1, dist[i][j]);
        }
    }

    public int minPathSum1(int[][] grid) {
        int m = grid.length;
        int n = 0;
        if (m != 0) {
            n = grid[0].length;
        }

        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dist[i][j] = grid[i][j];
                } else if (i == 0) {
                    dist[i][j] = dist[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    dist[i][j] = dist[i - 1][j] + grid[i][j];
                } else {
                    dist[i][j] = Math.min(dist[i - 1][j], dist[i][j - 1]) + grid[i][j];
                }
            }
        }

        return dist[m - 1][n - 1];
    }

    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    dp[j] = dp[j];
                } else if (i == 0) {
                    dp[j] = dp[j - 1];
                } else {
                    dp[j] = Math.min(dp[j], dp[j - 1]);
                }

                dp[j] += grid[i][j];
            }
        }

        return dp[n - 1];
    }
}
