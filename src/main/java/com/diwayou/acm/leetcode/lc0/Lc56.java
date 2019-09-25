package com.diwayou.acm.leetcode.lc0;

import java.util.Arrays;
import java.util.Comparator;

/**
 * https://leetcode-cn.com/problems/merge-intervals/
 */
public class Lc56 {

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(new Lc56().merge(new int[][]{{2, 5}, {1, 8}, {1, 6}})));
    }

    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return intervals;
        }

        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] < o2[0]) {
                    return -1;
                } else if (o1[0] > o2[0]) {
                    return 1;
                } else {
                    if (o1[1] < o2[1]) {
                        return -1;
                    } else if (o1[1] > o2[1]) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        });

        int j = 0;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] == intervals[j][0]) {
                // [1, 2] [1, 3]
                if (intervals[i][1] > intervals[j][1]) {
                    intervals[j][1] = intervals[i][1];
                }
            } else {
                // [1, 4] [2, 6]
                if (intervals[i][0] <= intervals[j][1] && intervals[i][1] > intervals[j][1]) {
                    intervals[j][1] = intervals[i][1];
                } else if (intervals[i][0] > intervals[j][1]) {
                    // [1, 4] [5, 6]
                    intervals[++j] = intervals[i];
                }
            }
        }

        return Arrays.copyOf(intervals, j + 1);
    }

    public int[][] merge1(int[][] intervals) {
        if (intervals.length == 0) {
            return intervals;
        }

        int m = intervals.length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            if (intervals[i] == null)
                continue;

            int p = intervals[i][0], q = intervals[i][1];
            for (int j = i + 1; j < m; j++) {
                int s = intervals[j][0], t = intervals[j][1];
                // [1, 10] [2, x], [3, x] [1, 10]
                if ((s >= p && s <= q) || (p >= s && p <= t)) {
                    intervals[j][0] = Math.min(p, s);
                    intervals[j][1] = Math.max(q, t);
                    intervals[i] = null;
                    count++;
                    break;
                }
            }
        }

        int[][] res = new int[m - count][2];
        int j = 0;
        for (int i = 0; i < m; i++) {
            if (intervals[i] != null)
                res[j++] = intervals[i];
        }

        return res;
    }
}
