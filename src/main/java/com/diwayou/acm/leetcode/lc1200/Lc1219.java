package com.diwayou.acm.leetcode.lc1200;

/**
 * https://leetcode-cn.com/problems/path-with-maximum-gold/
 *
 * 你要开发一座金矿，地质勘测学家已经探明了这座金矿中的资源分布，并用大小为 m * n 的网格 grid 进行了标注。每个单元格中的整数就表示这一
 * 单元格中的黄金数量；如果该单元格是空的，那么就是 0。
 * 为了使收益最大化，矿工需要按以下规则来开采黄金：
 * 每当矿工进入一个单元，就会收集该单元格中的所有黄金。
 * 矿工每次可以从当前位置向上下左右四个方向走。
 * 每个单元格只能被开采（进入）一次。
 * 不得开采（进入）黄金数目为 0 的单元格。
 * 矿工可以从网格中 任意一个 有黄金的单元格出发或者是停止。
 *  
 * 示例 1：
 * 输入：grid = [[0,6,0],[5,8,7],[0,9,0]]
 * 输出：24
 * 解释：
 * [[0,6,0],
 *  [5,8,7],
 *  [0,9,0]]
 * 一种收集最多黄金的路线是：9 -> 8 -> 7。
 *
 * 示例 2：
 * 输入：grid = [[1,0,7],[2,0,6],[3,4,5],[0,3,0],[9,0,20]]
 * 输出：28
 * 解释：
 * [[1,0,7],
 *  [2,0,6],
 *  [3,4,5],
 *  [0,3,0],
 *  [9,0,20]]
 * 一种收集最多黄金的路线是：1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7。
 *  
 * 提示：
 * 1 <= grid.length, grid[i].length <= 15
 * 0 <= grid[i][j] <= 100
 * 最多 25 个单元格中有黄金。
 */
public class Lc1219 {

    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0, 22, 0, 24},
                {34, 23, 18, 0, 23, 2},
                {11, 39, 20, 12, 0, 0},
                {39, 8, 0, 2, 0, 1},
                {19, 32, 26, 20, 20, 30},
                {0, 38, 26, 0, 29, 31}};

        System.out.println(new Lc1219().getMaximumGold(grid));
    }

    private int max = 0;

    public int getMaximumGold(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0 ||
                        // 排除重复遍历
                        (i > 0 && i < grid.length - 1 && grid[i - 1][j] > 0 && grid[i + 1][j] > 0) ||
                        (j > 0 && j < grid[0].length - 1 && grid[i][j - 1] > 0 && grid[i][j + 1] > 0)) {
                    continue;
                }

                dfs(grid, i, j, 0);
            }
        }

        return max;
    }

    private void dfs(int[][] grid, int i, int j, int m) {
        if (grid[i][j] == 0) {
            if (m > max) {
                max = m;
            }

            return;
        }

        int num = grid[i][j];
        int v = num + m;

        grid[i][j] = 0;

        if (i > 0) {
            dfs(grid, i - 1, j, v);
        }
        if (j > 0) {
            dfs(grid, i, j - 1, v);
        }
        if (i < grid.length - 1) {
            dfs(grid, i + 1, j, v);
        }
        if (j < grid[0].length - 1) {
            dfs(grid, i, j + 1, v);
        }

        grid[i][j] = num;
    }
}
