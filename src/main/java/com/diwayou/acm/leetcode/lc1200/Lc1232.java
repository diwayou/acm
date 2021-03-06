package com.diwayou.acm.leetcode.lc1200;

/**
 * https://leetcode-cn.com/problems/check-if-it-is-a-straight-line/
 * <p>
 * 在一个XY 坐标系中有一些点，我们用数组coordinates来分别记录它们的坐标，其中coordinates[i] = [x, y]表示横坐标为 x、纵坐标为 y的点。
 * 请你来判断，这些点是否在该坐标系中属于同一条直线上，是则返回 true，否则请返回 false。
 * <p>
 * 示例 1：
 * 输入：coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]
 * 输出：true
 * <p>
 * 示例 2：
 * 输入：coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]
 * 输出：false
 * <p>
 * 提示：
 * 2 <=coordinates.length <= 1000
 * coordinates[i].length == 2
 * -10^4 <=coordinates[i][0],coordinates[i][1] <= 10^4
 * coordinates中不含重复的点
 */
public class Lc1232 {

    public boolean checkStraightLine(int[][] coordinates) {
        if (coordinates.length <= 2) {
            return true;
        }

        if (!threeOneLine(coordinates[0], coordinates[1], coordinates[2])) {
            return false;
        }

        for (int i = 4; i < coordinates.length; i++) {
            if (!threeOneLine(coordinates[0], coordinates[1], coordinates[i])) {
                return false;
            }
        }

        return true;
    }

    private static boolean threeOneLine(int[] p0, int[] p1, int[] p2) {
        int x1 = p0[0] - p1[0];
        int x2 = p0[0] - p2[0];

        int y1 = p0[1] - p1[1];
        int y2 = p0[1] - p2[1];

        return x1 * y2 - x2 * y1 == 0;
    }
}
