package com.diwayou.acm.leetcode.lc1500;

/**
 * https://leetcode-cn.com/problems/count-unhappy-friends/
 *
 * 给你一份 n 位朋友的亲近程度列表，其中 n 总是 偶数 。
 * 对每位朋友 i，preferences[i] 包含一份 按亲近程度从高到低排列 的朋友列表。换句话说，排在列表前面的朋友与 i 的亲近程度比排在列表后面的朋友更高。
 * 每个列表中的朋友均以 0 到 n-1 之间的整数表示。
 * 所有的朋友被分成几对，配对情况以列表 pairs 给出，其中 pairs[i] = [xi, yi] 表示 xi 与 yi 配对，且 yi 与 xi 配对。
 * 但是，这样的配对情况可能会是其中部分朋友感到不开心。在 x 与 y 配对且 u 与 v 配对的情况下，如果同时满足下述两个条件，x 就会不开心：
 * x 与 u 的亲近程度胜过 x 与 y，且
 * u 与 x 的亲近程度胜过 u 与 v
 * 返回 不开心的朋友的数目 。
 *
 * 示例 1：
 * 输入：n = 4, preferences = [[1, 2, 3], [3, 2, 0], [3, 1, 0], [1, 2, 0]], pairs = [[0, 1], [2, 3]]
 * 输出：2
 * 解释：
 * 朋友 1 不开心，因为：
 * - 1 与 0 配对，但 1 与 3 的亲近程度比 1 与 0 高，且
 * - 3 与 1 的亲近程度比 3 与 2 高。
 * 朋友 3 不开心，因为：
 * - 3 与 2 配对，但 3 与 1 的亲近程度比 3 与 2 高，且
 * - 1 与 3 的亲近程度比 1 与 0 高。
 * 朋友 0 和 2 都是开心的。
 *
 * 示例 2：
 * 输入：n = 2, preferences = [[1], [0]], pairs = [[1, 0]]
 * 输出：0
 * 解释：朋友 0 和 1 都开心。
 *
 * 示例 3：
 * 输入：n = 4, preferences = [[1, 3, 2], [2, 3, 0], [1, 3, 0], [0, 2, 1]], pairs = [[1, 3], [0, 2]]
 * 输出：4
 *  
 * 提示：
 * 2 <= n <= 500
 * n 是偶数
 * preferences.length == n
 * preferences[i].length == n - 1
 * 0 <= preferences[i][j] <= n - 1
 * preferences[i] 不包含 i
 * preferences[i] 中的所有值都是独一无二的
 * pairs.length == n/2
 * pairs[i].length == 2
 * xi != yi
 * 0 <= xi, yi <= n - 1
 * 每位朋友都 恰好 被包含在一对中
 *
 * @author gaopeng
 * @date 2020/10/15
 */
public class Lc1583 {

    public static void main(String[] args) {
        int n = 4;
        int[][] preferences = new int[][]{{1, 2, 3}, {3, 2, 0},{3, 1, 0},{1, 2, 0}};
        int[][] pairs = new int[][]{{0, 1},{2,3}};

        System.out.println(new Lc1583().unhappyFriends(n, preferences, pairs));
    }

    public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
        int ans = 0;

        int[] pairIdx = new int[n];
        for (int[] pair : pairs) {
            pairIdx[pair[0]] = pair[1];
            pairIdx[pair[1]] = pair[0];
        }

        for (int[] pair : pairs) {
            if (check(preferences, pair[0], pair[1], pairIdx)) {
                ans++;
            }
            if (check(preferences, pair[1], pair[0], pairIdx)) {
                ans++;
            }
        }

        return ans;
    }

    private static boolean check(int[][] preferences, int x, int y, int[] pairIdx) {
        int[] p = preferences[x];
        for (int i = 0; i < p.length; i++) {
            int u = p[i];
            if (u == y) {
                break;
            }

            int v = pairIdx[u];
            for (int h : preferences[u]) {
                if (h == x) {
                    return true;
                }
                if (h == v) {
                    break;
                }
            }
        }

        return false;
    }
}