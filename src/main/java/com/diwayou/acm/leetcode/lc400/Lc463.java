package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/island-perimeter/
 *
 * 给定一个包含 0 和 1 的二维网格地图，其中 1 表示陆地 0 表示水域。
 * 网格中的格子水平和垂直方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
 * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
 *
 * 示例 :
 * 输入:
 * [[0,1,0,0],
 *  [1,1,1,0],
 *  [0,1,0,0],
 *  [1,1,0,0]]
 *
 * 输出: 16
 *
 * 解释: 它的周长是下面图片中的 16 个黄色的边：
 */
public class Lc463 {

    private int m;
    private int n;

    public int islandPerimeter(int[][] grid) {
        m = grid.length;
        n = grid[0].length;

        int re = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    re += dfs(grid, i, j);
                }
            }
        }

        return re;
    }

    private int dfs(int[][] grid, int i, int j) {
        int len = 4;
        if (i > 0 && grid[i - 1][j] == 1) {
            len--;
        }
        if (i < m - 1 && grid[i + 1][j] == 1) {
            len--;
        }
        if (j > 0 && grid[i][j - 1] == 1) {
            len--;
        }
        if (j < n - 1 && grid[i][j + 1] == 1) {
            len--;
        }

        return len;
    }

    public int islandPerimeter1(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    int side = 4;
                    if (i > 0 && grid[i - 1][j] == 1)
                        side -= 2;
                    if (j > 0 && grid[i][j - 1] == 1)
                        side -= 2;
                    res += side;
                }
            }
        }

        return res;
    }
}
