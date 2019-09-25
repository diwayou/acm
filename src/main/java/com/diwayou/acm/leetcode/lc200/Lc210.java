package com.diwayou.acm.leetcode.lc200;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/course-schedule-ii/
 */
public class Lc210 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                new int[]{1, 0},
        };
        System.out.println(Arrays.toString(new Lc210().findOrder(2, arr)));
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] adjMap = new List[numCourses];
        int[] inDegrees = new int[numCourses];

        for (int[] req : prerequisites) {
            ++inDegrees[req[0]];

            if (adjMap[req[1]] == null) {
                adjMap[req[1]] = new ArrayList<>();
            }
            adjMap[req[1]].add(req[0]);
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; ++i) {
            if (inDegrees[i] == 0) {
                queue.offer(i);
            }
        }

        Integer node;
        List<Integer> result = new ArrayList<>(numCourses);
        while (!queue.isEmpty()) {
            node = queue.poll();
            result.add(node);

            if (adjMap[node] == null) {
                continue;
            }

            for (int adj : adjMap[node]) {
                if (--inDegrees[adj] == 0) {
                    queue.offer(adj);
                }
            }
        }

        if (result.size() != numCourses) {
            return new int[0];
        }

        int[] re = new int[numCourses];
        for (int i = 0; i < result.size(); ++i) {
            re[i] = result.get(i);
        }

        return re;
    }

    private List<Integer>[] adj;
    private boolean[] marked;
    private boolean[] onStack; // 递归调用栈上的所有顶点
    private int[] result;
    private int point = 0;

    public int[] findOrder1(int numCourses, int[][] prerequisites) {
        onStack = new boolean[numCourses];
        marked = new boolean[numCourses];
        adj = new ArrayList[numCourses];
        result = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < prerequisites.length; i++) {
            adj[prerequisites[i][0]].add(prerequisites[i][1]);
        }

        for (int v = 0; v < numCourses; v++) {
            if (!marked[v]) {
                if (!dfs(v))
                    return new int[0];
            }
        }
        return result;

    }

    public boolean dfs(int v) {
        marked[v] = true;
        onStack[v] = true;
        for (int w : adj[v]) {
            if (!marked[w]) {
                if (!dfs(w))
                    return false;
            } else if (onStack[w]) {
                return false;
            }
        }
        onStack[v] = false;
        result[point++] = v;

        return true;
    }
}
