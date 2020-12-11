package com.diwayou.acm.leetcode.lc900;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/minimize-malware-spread-ii/
 * <p>
 * (这个问题与尽量减少恶意软件的传播是一样的，不同之处用粗体表示。)
 * 在节点网络中，只有当graph[i][j] = 1时，每个节点i能够直接连接到另一个节点j。
 * 一些节点initial最初被恶意软件感染。只要两个节点直接连接，且其中至少一个节点受到恶意软件的感染，那么两个节点都将被恶意软件感染。这种恶意软件的传播将继续，直到没有更多的节点可以被这种方式感染。
 * 假设M(initial)是在恶意软件停止传播之后，整个网络中感染恶意软件的最终节点数。
 * 我们可以从初始列表中删除一个节点，并完全移除该节点以及从该节点到任何其他节点的任何连接。如果移除这一节点将最小化M(initial)，则返回该节点。如果有多个节点满足条件，就返回索引最小的节点。
 * <p>
 * 示例 1：
 * 输出：graph = [[1,1,0],[1,1,0],[0,0,1]], initial = [0,1]
 * 输入：0
 * <p>
 * 示例 2：
 * 输入：graph = [[1,1,0],[1,1,1],[0,1,1]], initial = [0,1]
 * 输出：1
 * <p>
 * 示例 3：
 * 输入：graph = [[1,1,0,0],[1,1,1,0],[0,1,1,1],[0,0,1,1]], initial = [0,1]
 * 输出：1
 * <p>
 * 提示：
 * 1 < graph.length = graph[0].length <= 300
 * 0 <= graph[i][j] == graph[j][i] <= 1
 * graph[i][i] = 1
 * 1 <= initial.length < graph.length
 * 0 <= initial[i] < graph.length
 */
public class Lc928 {

    public int minMalwareSpread(int[][] graph, int[] initial) {
        int N = graph.length;
        boolean[] clean = new boolean[N];
        for (int x : initial) {
            clean[x] = true;
        }

        ArrayList<Integer>[] infectedBy = new ArrayList[N];
        for (int u : initial) {
            boolean[] seen = new boolean[N];
            dfs(graph, clean, u, seen);

            for (int v = 0; v < N; v++) {
                if (seen[v]) {
                    if (infectedBy[v] == null) {
                        infectedBy[v] = new ArrayList<>();
                    }

                    infectedBy[v].add(u);
                }
            }
        }

        int[] contribution = new int[N];
        for (int v = 0; v < N; ++v) {
            if (infectedBy[v] != null && infectedBy[v].size() == 1) {
                contribution[infectedBy[v].get(0)]++;
            }
        }

        int ans = initial[0], ansSize = -1;
        for (int u : initial) {
            int score = contribution[u];
            if (score > ansSize || score == ansSize && u < ans) {
                ans = u;
                ansSize = score;
            }
        }

        return ans;
    }

    public void dfs(int[][] graph, boolean[] clean, int u, boolean[] seen) {
        for (int v = 0; v < graph.length; ++v)
            if (graph[u][v] == 1 && !clean[v] && !seen[v]) {
                seen[v] = true;
                dfs(graph, clean, v, seen);
            }
    }

    public int minMalwareSpread2(int[][] graph, int[] initial) {
        int N = graph.length;
        boolean[] malwares = new boolean[N];
        for (int i : initial) {
            malwares[i] = true;
        }

        int safe = 0, removeId = initial[0];
        for (int m : initial) {
            boolean[] visited = new boolean[N];
            visited[m] = true;
            int cur = 0;
            for (int i = 0; i < N; i++) {
                if (graph[m][i] == 1 && !visited[i]) {
                    int curSafe = dfs(i, malwares, graph, visited);
                    if (curSafe > 0) {
                        cur += curSafe;
                    }
                }
            }

            if (cur > safe || cur == safe && removeId > m) {
                safe = cur;
                removeId = m;
            }
        }

        return removeId;
    }

    private int dfs(int id, boolean[] malwares, int[][] graph, boolean[] visited) {
        if (malwares[id]) {
            return -1;
        }

        visited[id] = true;
        int sum = 1;
        for (int i = 0; i < graph.length; i++) {
            if (graph[id][i] == 1 && !visited[i]) {
                int c = dfs(i, malwares, graph, visited);
                if (c == -1) {
                    malwares[i] = true;
                    return -1;
                } else {
                    sum += c;
                }
            }
        }

        return sum;
    }

    public int minMalwareSpread1(int[][] graph, int[] initial) {
        int N = graph.length;
        UF uf = new UF(N);

        boolean[] clean = new boolean[N];
        for (int x : initial) {
            clean[x] = true;
        }

        for (int u = 0; u < N; ++u) {
            if (!clean[u]) {
                for (int v = 0; v < N; ++v) {
                    if (!clean[v]) {
                        if (graph[u][v] == 1) {
                            uf.union(u, v);
                        }
                    }
                }
            }
        }

        int[] count = new int[N];
        Map<Integer, Set<Integer>> nodeToCompo = new HashMap<>();
        for (int u : initial) {
            Set<Integer> components = new HashSet<>();
            for (int v = 0; v < N; ++v) {
                if (!clean[v]) {
                    if (graph[u][v] == 1) {
                        components.add(uf.find(v));
                    }
                }
            }

            nodeToCompo.put(u, components);
            for (int c : components) {
                count[c]++;
            }
        }

        int ans = -1, ansSize = -1;
        for (int u : nodeToCompo.keySet()) {
            Set<Integer> components = nodeToCompo.get(u);
            int score = 0;
            for (int c : components) {
                if (count[c] == 1) {
                    score += uf.size(c);
                }
            }

            if (score > ansSize || score == ansSize && u < ans) {
                ansSize = score;
                ans = u;
            }
        }

        return ans;
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

        public int size(int x) {
            return sz[find(x)];
        }
    }
}
