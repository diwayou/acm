package com.diwayou.acm.leetcode.lc300;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/minimum-height-trees/
 * <p>
 * 对于一个具有树特征的无向图，我们可选择任何一个节点作为根。图因此可以成为树，在所有可能的树中，具有最小高度的树被称为最小高度树。给出这样的一个图，写出一个函数找到所有的最小高度树并返回他们的根节点。
 * <p>
 * 格式
 * 该图包含n个节点，标记为0到n - 1。给定数字n和一个无向边edges列表（每一个边都是一对标签）。
 * 你可以假设没有重复的边会出现在edges中。由于所有的边都是无向边， [0, 1]和[1, 0]是相同的，因此不会同时出现在edges里。
 * <p>
 * 示例 1:
 * 输入: n = 4, edges = [[1, 0], [1, 2], [1, 3]]
 * <p>
 * 0
 * |
 * 1
 * / \
 * 2   3
 * 输出: [1]
 * <p>
 * 示例 2:
 * 输入: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
 * <p>
 * 0  1  2
 * \ | /
 * 3
 * |
 * 4
 * |
 * 5
 * <p>
 * 输出: [3, 4]
 * <p>
 * 说明:
 * 根据树的定义，树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，一个任何没有简单环路的连通图都是一棵树。
 * 树的高度是指根节点和叶子节点之间最长向下路径上边的数量。
 */
public class Lc310 {

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return Collections.singletonList(0);
        }

        int[] degree = new int[n];
        List<Integer>[] g = new List[n];

        for (int[] e : edges) {
            if (g[e[0]] == null) {
                g[e[0]] = new ArrayList<>();
            }
            if (g[e[1]] == null) {
                g[e[1]] = new ArrayList<>();
            }
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);

            degree[e[0]]++;
            degree[e[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                queue.offer(i);
            }
        }

        int rst = n;

        while (rst != 1 && rst != 2) {
            int len = queue.size();
            rst -= len;
            for (int i = 0; i < len; i++) {
                int cur = queue.poll();
                for (int v : g[cur]) {
                    if (degree[v] > 0)
                        degree[v]--;
                    if (degree[v] == 1)
                        queue.offer(v);
                }
            }
        }

        List<Integer> re = new ArrayList<>(queue.size());
        while (!queue.isEmpty()) {
            re.add(queue.poll());
        }

        return re;
    }
}
