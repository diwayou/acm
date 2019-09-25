package com.diwayou.acm.leetcode.lc600;

import java.util.Arrays;
import java.util.Comparator;

/**
 * https://leetcode-cn.com/classic/problems/maximum-length-of-pair-chain/description/
 * <p>
 * 给出 n 个数对。 在每一个数对中，第一个数字总是比第二个数字小。
 * 现在，我们定义一种跟随关系，当且仅当 b < c 时，数对(c, d) 才可以跟在 (a, b) 后面。我们用这种形式来构造一个数对链。
 * 给定一个对数集合，找出能够形成的最长数对链的长度。你不需要用到所有的数对，你可以以任何顺序选择其中的一些数对来构造。
 */
public class Lc646 {
    public static void main(String[] args) {
        //int[][] pairs = new int[][]{{-10, -8}, {8, 9}, {-5, 0}, {6, 10}, {-6, -4}, {1, 7}, {9, 10}, {-4, 7}};
        int[][] pairs = new int[][]{{-6, 9}, {1, 6}, {8, 10}, {-1, 4}, {-6, -2}, {-9, 8}, {-5, 3}, {0, 3}};
        //int[][] pairs = new int[][]{{1, 2}, {2, 3}, {3, 4}};

        System.out.println(new Lc646().findLongestChain(pairs));
    }

    private int result = 1;

    public int findLongestChain1(int[][] pairs) {
        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] < o2[0]) {
                    return -1;
                }
                if (o1[0] > o2[0]) {
                    return 1;
                } else {
                    return Integer.compare(o1[1], o2[1]);
                }
            }
        });

        int[] cache = new int[pairs.length];
        Arrays.fill(cache, -1);

        return findLongestChainHelper(pairs, pairs.length - 1, cache);
    }

    private int findLongestChainHelper(int[][] pairs, int i, int[] cache) {
        if (i < 0) {
            return 0;
        }

        if (cache[i] != -1) {
            return cache[i];
        }

        int max = 1;
        int len = 0;
        for (int j = 0; j < i; j++) {
            len = findLongestChainHelper(pairs, j, cache);
            if (pairs[i][0] > pairs[j][1]) {
                len++;
            }

            if (len > max) {
                max = len;
            }
        }

        if (max > result) {
            result = max;
        }

        cache[i] = max;

        return max;
    }

    public int findLongestChain2(int[][] pairs) {
        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] < o2[0]) {
                    return -1;
                }
                if (o1[0] > o2[0]) {
                    return 1;
                } else {
                    return Integer.compare(o1[1], o2[1]);
                }
            }
        });

        int[] dp = new int[pairs.length];
        int len;
        int max;
        int result = 1;
        for (int i = 0; i < pairs.length; i++) {
            max = 1;
            for (int j = 0; j < i; j++) {
                len = dp[j];
                if (pairs[i][0] > pairs[j][1]) {
                    len++;
                }

                if (len > max) {
                    max = len;
                }
            }

            dp[i] = max;
            result = Math.max(max, result);
        }

        return result;
    }

    /**
     * 从这道题的要求来看，按照区间结束值排序更容易优化动态规划流程
     */
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] == o2[1] ? o1[0] - o2[0] : o1[1] - o2[1];
            }
        });

        int result = 1;
        int lastMax = pairs[0][1];
        for (int i = 1; i < pairs.length; i++) {
            if (pairs[i][0] > lastMax) {
                result++;
                lastMax = pairs[i][1];
            }
        }

        return result;
    }
}
