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

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] inDegrees = new int[numCourses];
        List<Integer>[] adjMap = new List[numCourses];

        Queue<Integer> queue = new LinkedList<>();
        for (int[] req : prerequisites) {
            ++inDegrees[req[0]];
            List<Integer> adj = adjMap[req[1]];
            if (adj == null) {
                adj = new ArrayList<>();
                adjMap[req[1]] = adj;
            }

            adj.add(req[0]);
        }

        for (int i = 0; i < numCourses; ++i) {
            if (inDegrees[i] == 0) {
                queue.offer(i);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            Integer num = queue.poll();
            ++count;

            if (adjMap[num] == null) {
                continue;
            }

            for (int adj : adjMap[num]) {
                if (--inDegrees[adj] == 0) {
                    queue.offer(adj);
                }
            }
        }

        return count == numCourses;
    }

    public boolean canFinish1(int numCourses, int[][] prerequisites) {
        List<Integer>[] graphic = new List[numCourses];

        for (int[] pre : prerequisites) {
            if (graphic[pre[0]] == null) {
                graphic[pre[0]] = new ArrayList<>();
            }
            graphic[pre[0]].add(pre[1]);
        }

        boolean[] globalMarked = new boolean[numCourses];
        boolean[] localMarked = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(globalMarked, localMarked, graphic, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCycle(boolean[] globalMarked, boolean[] localMarked,
                             List<Integer>[] graphic, int curNode) {
        if (localMarked[curNode]) {
            return true;
        }
        if (globalMarked[curNode]) {
            return false;
        }
        globalMarked[curNode] = true;

        if (graphic[curNode] != null) {
            localMarked[curNode] = true;
            for (int nextNode : graphic[curNode]) {
                if (hasCycle(globalMarked, localMarked, graphic, nextNode)) {
                    return true;
                }
            }
            localMarked[curNode] = false;
        }

        return false;
    }
}
