package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/projection-area-of-3d-shapes/
 * <p>
 * 在N*N的网格中，我们放置了一些与 x，y，z 三轴对齐的1 * 1 * 1立方体。
 * 每个值v = grid[i][j]表示 v个正方体叠放在单元格(i, j)上。
 * 现在，我们查看这些立方体在 xy、yz和 zx平面上的投影。
 * 投影就像影子，将三维形体映射到一个二维平面上。
 * 在这里，从顶部、前面和侧面看立方体时，我们会看到“影子”。
 * 返回所有三个投影的总面积。
 * <p>
 * 示例 1：
 * 输入：[[2]]
 * 输出：5
 * <p>
 * 示例 2：
 * 输入：[[1,2],[3,4]]
 * 输出：17
 * 解释：
 * 这里有该形体在三个轴对齐平面上的三个投影(“阴影部分”)。
 * <p>
 * 示例 3：
 * 输入：[[1,0],[0,2]]
 * 输出：8
 * <p>
 * 示例 4：
 * 输入：[[1,1,1],[1,0,1],[1,1,1]]
 * 输出：14
 * <p>
 * 示例 5：
 * 输入：[[2,2,2],[2,1,2],[2,2,2]]
 * 输出：21
 * <p>
 * 提示：
 * 1 <= grid.length = grid[0].length<= 50
 * 0 <= grid[i][j] <= 50
 */
public class Lc883 {

    public int projectionArea(int[][] grid) {
        int N = grid.length;
        int ans = 0;

        for (int i = 0; i < N; ++i) {
            int bestRow = 0;  // largest of grid[i][j]
            int bestCol = 0;  // largest of grid[j][i]
            for (int j = 0; j < N; ++j) {
                if (grid[i][j] > 0) ans++;  // top shadow
                bestRow = Math.max(bestRow, grid[i][j]);
                bestCol = Math.max(bestCol, grid[j][i]);
            }
            ans += bestRow + bestCol;
        }

        return ans;
    }
}
