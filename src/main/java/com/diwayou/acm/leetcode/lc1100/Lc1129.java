package com.diwayou.acm.leetcode.lc1100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/shortest-path-with-alternating-colors/
 * <p>
 * 在一个有向图中，节点分别标记为0, 1, ..., n-1。这个图中的每条边不是红色就是蓝色，且存在自环或平行边。
 * red_edges中的每一个[i, j]对表示从节点 i 到节点 j 的红色有向边。类似地，blue_edges中的每一个[i, j]对表示从节点 i 到节点 j 的蓝色有向边。
 * 返回长度为 n 的数组answer，其中answer[X]是从节点0到节点X的最短路径的长度，且路径上红色边和蓝色边交替出现。如果不存在这样的路径，那么 answer[x] = -1。
 * <p>
 * 示例 1：
 * 输入：n = 3, red_edges = [[0,1],[1,2]], blue_edges = []
 * 输出：[0,1,-1]
 * <p>
 * 示例 2：
 * 输入：n = 3, red_edges = [[0,1]], blue_edges = [[2,1]]
 * 输出：[0,1,-1]
 * <p>
 * 示例 3：
 * 输入：n = 3, red_edges = [[1,0]], blue_edges = [[2,1]]
 * 输出：[0,-1,-1]
 * <p>
 * 示例 4：
 * 输入：n = 3, red_edges = [[0,1]], blue_edges = [[1,2]]
 * 输出：[0,1,2]
 * <p>
 * 示例 5：
 * 输入：n = 3, red_edges = [[0,1],[0,2]], blue_edges = [[1,0]]
 * 输出：[0,1,1]
 * <p>
 * 提示：
 * 1 <= n <= 100
 * red_edges.length <= 400
 * blue_edges.length <= 400
 * red_edges[i].length == blue_edges[i].length == 2
 * 0 <= red_edges[i][j], blue_edges[i][j] < n
 */
public class Lc1129 {

    public static void main(String[] args) {
        int[][] red = new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        int[][] blue = new int[][]{{1, 2}, {2, 3}, {3, 1}};
        System.out.println(Arrays.toString(new Lc1129().shortestAlternatingPaths(5, red, blue)));
    }

    private int[][] answer;
    private List[][] edges;

    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        edges = new List[2][];
        edges[0] = new List[n];
        edges[1] = new List[n];

        for (int[] e : red_edges) {
            if (edges[0][e[0]] == null) {
                edges[0][e[0]] = new ArrayList();
            }

            edges[0][e[0]].add(e[1]);
        }
        for (int[] e : blue_edges) {
            if (edges[1][e[0]] == null) {
                edges[1][e[0]] = new ArrayList();
            }

            edges[1][e[0]].add(e[1]);
        }

        answer = new int[n][2];
        for (int i = 1; i < answer.length; i++) {
            answer[i][0] = Integer.MAX_VALUE;
            answer[i][1] = Integer.MAX_VALUE;
        }
        answer[0][0] = 0;
        answer[0][1] = 0;

        // 红
        dfs(0, true, 0);
        // 蓝
        dfs(0, false, 0);

        int[] re = new int[n];
        for (int i = 0; i < re.length; i++) {
            re[i] = Math.min(answer[i][0], answer[i][1]);
            if (re[i] == Integer.MAX_VALUE) {
                re[i] = -1;
            }
        }

        return re;
    }

    private static int b2i(boolean b) {
        return b ? 1 : 0;
    }

    private void dfs(int i, boolean color, int len) {
        List<Integer> es = edges[b2i(color)][i];
        if (es == null) {
            return;
        }

        for (int e : es) {
            if (answer[e][b2i(color)] > 1 + len) {
                answer[e][b2i(color)] = 1 + len;

                dfs(e, !color, len + 1);
            }
        }
    }
}
