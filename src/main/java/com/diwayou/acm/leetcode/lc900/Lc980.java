package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/unique-paths-iii/
 *
 * 在二维网格 grid 上，有 4 种类型的方格：
 * 1 表示起始方格。且只有一个起始方格。
 * 2 表示结束方格，且只有一个结束方格。
 * 0 表示我们可以走过的空方格。
 * -1 表示我们无法跨越的障碍。
 * 返回在四个方向（上、下、左、右）上行走时，从起始方格到结束方格的不同路径的数目，每一个无障碍方格都要通过一次。
 *
 * 示例 1：
 * 输入：[[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
 * 输出：2
 * 解释：我们有以下两条路径：
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
 * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
 *
 * 示例 2：
 * 输入：[[1,0,0,0],[0,0,0,0],[0,0,0,2]]
 * 输出：4
 * 解释：我们有以下四条路径：
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
 * 2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
 * 3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
 * 4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
 *
 * 示例 3：
 * 输入：[[0,1],[2,0]]
 * 输出：0
 * 解释：
 * 没有一条路能完全穿过每一个空的方格一次。
 * 请注意，起始和结束方格可以位于网格中的任意位置。
 * 
 * 提示：
 * 1 <= grid.length * grid[0].length <= 20
 */
public class Lc980 {

    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {1,0,0,0},
                {0,0,0,0},
                {0,0,2,-1}
        };

        System.out.println(new Lc980().uniquePathsIII(grid));
    }

    private int m;

    private int n;

    private int z;

    private int si;

    private int sj;

    private int re = 0;

    public int uniquePathsIII(int[][] grid) {
        m = grid.length;
        n = grid[0].length;

        int v;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                v = grid[i][j];
                if (v == 0) {
                    z++;
                } else if (v == 1) {
                    si = i;
                    sj = j;
                }
            }
        }

        dfs(grid, si, sj, z + 1);

        return re;
    }

    private void dfs(int[][] grid, int i, int j, int z) {
        int v = grid[i][j];
        if (v == 2) {
            if (z == 0) {
                re++;
            }

            return;
        }

        if (v == -1) {
            return;
        }

        grid[i][j] = -1;

        if (i > 0) {
            dfs(grid, i - 1, j, z - 1);
        }
        if (i < m - 1) {
            dfs(grid, i + 1, j, z - 1);
        }
        if (j > 0) {
            dfs(grid, i, j - 1, z - 1);
        }
        if (j < n - 1) {
            dfs(grid, i, j + 1, z - 1);
        }

        grid[i][j] = v;
    }
}
