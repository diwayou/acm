package com.diwayou.acm.leetcode.lc200;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/course-schedule/
 */
public class Lc207 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                new int[]{1, 0},
        };
        System.out.println(new Lc207().canFinish1(2, arr));
    }

    public boolean canFinish1(int numCourses, int[][] prerequisites) {
        int[] inDegrees = new int[numCourses];
        List<Integer>[] g = new List[numCourses];

        Queue<Integer> queue = new LinkedList<>();
        for (int[] req : prerequisites) {
            ++inDegrees[req[0]];
            if (g[req[1]] == null) {
                g[req[1]] = new ArrayList<>();
            }

            g[req[1]].add(req[0]);
        }

        for (int i = 0; i < numCourses; ++i) {
            if (inDegrees[i] == 0) {
                queue.offer(i);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int v = queue.poll();
            ++count;

            if (g[v] == null) {
                continue;
            }

            for (int adj : g[v]) {
                if (--inDegrees[adj] == 0) {
                    queue.offer(adj);
                }
            }
        }

        return count == numCourses;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] g = new List[numCourses];

        for (int[] req : prerequisites) {
            if (g[req[1]] == null) {
                g[req[1]] = new ArrayList<>();
            }

            g[req[1]].add(req[0]);
        }

        int[] mark = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(mark, g, i)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasCycle(int[] mark, List<Integer>[] g, int v) {
        if (mark[v] == 1) {
            return true;
        }
        if (mark[v] == 2) {
            return false;
        }

        if (g[v] != null) {
            mark[v] = 1;
            for (int adj : g[v]) {
                if (hasCycle(mark, g, adj)) {
                    return true;
                }
            }
        }

        mark[v] = 2;

        return false;
    }
}
