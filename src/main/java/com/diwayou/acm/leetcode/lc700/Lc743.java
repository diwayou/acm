package com.diwayou.acm.leetcode.lc700;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/network-delay-time/
 *
 * 有 N 个网络节点，标记为 1 到 N。
 * 给定一个列表 times，表示信号经过有向边的传递时间。 times[i] = (u, v, w)，其中 u 是源节点，v 是目标节点， w 是一个信号从源节点传递到目标节点的时间。
 * 现在，我们向当前的节点 K 发送了一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1。
 *
 * 注意:
 * N 的范围在 [1, 100] 之间。
 * K 的范围在 [1, N] 之间。
 * times 的长度在 [1, 6000] 之间。
 * 所有的边 times[i] = (u, v, w) 都有 1 <= u, v <= N 且 0 <= w <= 100。
 */
public class Lc743 {

    public static void main(String[] args) {
        int[][] times = {{2,1,1},{2,3,1},{3,4,1}};
        int N = 4;
        int K = 2;

        System.out.println(new Lc743().networkDelayTime(times, N, K));
    }

    private int[][] graph;

    private int[] distTo;

    private List<int[]> pq;

    public int networkDelayTime(int[][] times, int N, int K) {
        graph = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(graph[i], -1);
        }
        // 可以优化为堆
        pq = new ArrayList<>();
        distTo = new int[N];
        Arrays.fill(distTo, Integer.MAX_VALUE);

        for (int i = 0; i < times.length; i++) {
            int[] t = times[i];
            graph[t[0] - 1][t[1] - 1] = t[2];
        }

        int k = K - 1;
        distTo[k] = 0;
        pq.add(new int[]{k, distTo[k]});

        while (!pq.isEmpty()) {
            int[] t = removeMin();

            for (int i = 0; i < N; i++) {
                if (graph[t[0]][i] >= 0) {
                    relax(t[0], i, graph[t[0]][i]);
                }
            }
        }

        int re = 0;
        for (int i = 0; i < distTo.length; i++) {
            if (distTo[i] == Integer.MAX_VALUE) {
                return -1;
            }

            if (distTo[i] > re) {
                re = distTo[i];
            }
        }

        return re;
    }

    private int[] removeMin() {
        int[] min = pq.get(0), t;
        int idx = 0;
        for (int i = 1; i < pq.size(); i++) {
            t = pq.get(i);
            if (t[1] < min[1]) {
                min = t;
                idx = i;
            }
        }
        pq.remove(idx);

        return min;
    }

    private void updateOrAdd(int[] d) {
        int i;
        for (i = 0; i < pq.size(); i++) {
            if (pq.get(i)[0] == d[0]) {
                pq.get(i)[1] = d[1];
            }
        }

        if (i == pq.size()) {
            pq.add(d);
        }
    }

    private void relax(int v, int w, int weight) {
        if (distTo[w] > distTo[v] + weight) {
            distTo[w] = distTo[v] + weight;

            updateOrAdd(new int[]{w, distTo[w]});
        }
    }

    public int networkDelayTime1(int[][] times, int N, int K) {
        int[][] m = new int[N][N];
        int MAX_VAL = 9999;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    m[i][j] = 0;
                } else {
                    m[i][j] = MAX_VAL;
                }
            }
        }

        for (int i = 0; i < times.length; i++) {
            int[] edge = times[i];
            m[edge[0] - 1][edge[1] - 1] = edge[2];
        }

        int[] dst = new int[N];
        int[] mark = new int[N];
        int[] prev = new int[N];

        for (int i = 0; i < N; i++) {
            dst[i] = m[K - 1][i];
            mark[i] = 0;
        }

        mark[K-1] = 1;

        for (int i = 0; i < N; i++) {
            int min = MAX_VAL;
            int u = 0;
            for (int j = 0; j < N; j++) {
                if (mark[j] == 0 && dst[j] < min) {
                    min = dst[j];
                    u = j;
                }
            }
            mark[u] = 1;
            for (int k = 0; k < N; k++) {
                if (mark[k] == 0 && (dst[k] > dst[u] + m[u][k])) {
                    dst[k] = dst[u] + m[u][k];
                }
            }
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            if (max < dst[i]) {
                max = dst[i];
            }
        }
        if (max == MAX_VAL) {

            return -1;
        }

        return max;
    }
}
