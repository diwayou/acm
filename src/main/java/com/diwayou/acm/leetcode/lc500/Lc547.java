package com.diwayou.acm.leetcode.lc500;

/**
 * https://leetcode-cn.com/problems/friend-circles/
 * <p>
 * 班上有N名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B的朋友，B 是 C的朋友，那么我们可以认为 A 也是 C的朋友。所谓的朋友圈，是指所有朋友的集合。
 * 给定一个N * N的矩阵M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。
 * <p>
 * 示例 1:
 * 输入:
 * [[1,1,0],
 * [1,1,0],
 * [0,0,1]]
 * 输出: 2
 * 说明：已知学生0和学生1互为朋友，他们在一个朋友圈。
 * 第2个学生自己在一个朋友圈。所以返回2。
 * <p>
 * 示例 2:
 * 输入:
 * [[1,1,0],
 * [1,1,1],
 * [0,1,1]]
 * 输出: 1
 * 说明：已知学生0和学生1互为朋友，学生1和学生2互为朋友，所以学生0和学生2也是朋友，所以他们三个在一个朋友圈，返回1。
 * <p>
 * 注意：
 * N 在[1,200]的范围内。
 * 对于所有学生，有M[i][i] = 1。
 * 如果有M[i][j] = 1，则有M[j][i] = 1。
 */
public class Lc547 {

    private int n;
    private boolean[] mark;

    public int findCircleNum(int[][] M) {
        n = M.length;
        mark = new boolean[n];

        int p = 0;
        for (int i = 0; i < n; i++) {
            if (!mark[i]) {
                dfs(M, i);
                p++;
            }
        }

        return p;
    }

    private void dfs(int[][] m, int i) {
        mark[i] = true;

        for (int j = 0; j < n; j++) {
            if (!mark[j] && m[i][j] == 1) {
                dfs(m, j);
            }
        }
    }

    public int findCircleNum1(int[][] M) {
        UF uf = new UF(M.length);

        for (int i = 0; i < M.length; i++) {
            for (int j = i + 1; j < M.length; j++) {
                if (M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }

        return uf.count;
    }

    private static class UF {
        int[] parent;
        int[] sz;
        int count;

        UF(int n) {
            parent = new int[n];
            sz = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                sz[i] = 1;
            }
        }

        int find(int x) {
            while (parent[x] != x) {
                x = parent[x];
            }

            return x;
        }

        void union(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }

            if (sz[x] < sz[y]) {
                parent[x] = y;
                sz[y] += sz[x];
            } else {
                parent[y] = x;
                sz[x] += sz[y];
            }

            count--;
        }
    }
}
