package com.diwayou.acm.leetcode.lc0;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/unique-paths/
 * <p>
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * 问总共有多少条不同的路径？
 * <p>
 * 例如，上图是一个7 x 3 的网格。有多少可能的路径？
 * <p>
 * 说明：m和 n 的值均不超过 100。
 * <p>
 * 示例1:
 * 输入: m = 3, n = 2
 * 输出: 3
 * 解释:
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向右 -> 向下
 * 2. 向右 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向右
 * <p>
 * 示例2:
 * 输入: m = 7, n = 3
 * 输出: 28
 */
public class Lc62 {

    public static void main(String[] args) {
        System.out.println(new Lc62().uniquePaths(7, 3));
    }

    public int uniquePaths(int m, int n) {
        int[] dp = new int[m];
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[j] += dp[j - 1];
            }
        }

        return dp[m - 1];
    }
}
