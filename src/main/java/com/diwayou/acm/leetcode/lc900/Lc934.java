package com.diwayou.acm.leetcode.lc900;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/shortest-bridge/
 * <p>
 * 在给定的二维二进制数组A中，存在两座岛。（岛是由四面相连的 1 形成的一个最大组。）
 * 现在，我们可以将0变为1，以使两座岛连接起来，变成一座岛。
 * 返回必须翻转的0 的最小数目。（可以保证答案至少是 1。）
 * <p>
 * 1 <= A.length = A[0].length <= 100
 * A[i][j] == 0 或 A[i][j] == 1
 * <p>
 * 输入：[[0,1],[1,0]]
 * 输出：1
 * <p>
 * 输入：[[0,1,0],[0,0,0],[0,0,1]]
 * 输出：2
 * <p>
 * 输入：[[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
 * 输出：1
 */
public class Lc934 {

    public static void main(String[] args) {
        int[][] a = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };

        System.out.println(new Lc934().shortestBridge(a));
    }

    private int min = Integer.MAX_VALUE;

    public int shortestBridge(int[][] A) {
        int color = -2;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (A[i][j] != 1) {
                    continue;
                }

                dfs(A, i, j, color);
                color--;
            }
        }

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (A[i][j] != -2) {
                    continue;
                }

                findDfs(A, i - 1, j, 0);
                findDfs(A, i + 1, j, 0);
                findDfs(A, i, j - 1, 0);
                findDfs(A, i, j + 1, 0);
            }
        }

        return min;
    }

    private void findDfs(int[][] a, int i, int j, int len) {
        if (i < 0 || i >= a.length || j < 0 || j >= a.length) {
            return;
        }
        if (a[i][j] == -2 || len >= min) {
            return;
        }

        if (a[i][j] == -3) {
            if (len < min) {
                min = len;
            }
            return;
        }

        if (a[i][j] == 0 || a[i][j] > len) {
            a[i][j] = len;
            findDfs(a, i - 1, j, len + 1);
            findDfs(a, i + 1, j, len + 1);
            findDfs(a, i, j - 1, len + 1);
            findDfs(a, i, j + 1, len + 1);
        }
    }

    private void dfs(int[][] a, int i, int j, int color) {
        if (i < 0 || i >= a.length || j < 0 || j >= a.length) {
            return;
        }
        if (a[i][j] != 1) {
            return;
        }

        a[i][j] = color;

        dfs(a, i - 1, j, color);
        dfs(a, i + 1, j, color);
        dfs(a, i, j - 1, color);
        dfs(a, i, j + 1, color);
    }

    public int shortestBridge1(int[][] A) {
        int[][] dir = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        Queue<int[]> queue = new LinkedList<>();
        int row = A.length;
        int column = A[0].length;
        boolean flag = false;
        //找出岛的所有节点
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (!flag && A[i][j] == 1) {
                    dfs(A, i, j);
                    flag = true;
                }
                if (flag && A[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        //改变其坐标对应值,再找出第二个岛
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int m = 0; m < size; m++) {
                int[] temp = queue.poll();
                for (int[] d : dir) {
                    int x = temp[0] + d[0];
                    int y = temp[1] + d[1];
                    if (x < 0 || x >= A.length || y < 0 || y >= A[0].length || A[x][y] == 1)
                        continue;
                    if (A[x][y] == 2)
                        return step;
                    if (A[x][y] == 0) {
                        A[x][y] = 1;
                        queue.offer(new int[]{x, y});
                    }

                }
            }
            step++;
        }

        return 1;
    }

    public void dfs(int[][] A, int row, int col) {
        if (row < 0 || col < 0 || row >= A.length || col >= A[0].length || A[row][col] == 0 || A[row][col] == 2) {
            return;
        }
        A[row][col] = 2;
        dfs(A, row, col + 1);
        dfs(A, row + 1, col);
        dfs(A, row, col - 1);
        dfs(A, row - 1, col);
    }
}
