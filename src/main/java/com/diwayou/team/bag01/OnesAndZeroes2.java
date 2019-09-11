package com.diwayou.team.bag01;

import java.util.Arrays;

public class OnesAndZeroes2 {
    public static void main(String[] args) {
        String[] strs = new String[]{"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;

        System.out.println(new OnesAndZeroes2().findMaxForm(strs, m, n));
    }

    public int findMaxForm(String[] strs, int m, int n) {
        if (strs.length == 0) {
            return 0;
        }

        int[] zeroes = new int[strs.length];
        int[] ones = new int[strs.length];
        for (int i = 0; i < strs.length; ++i) {
            for (int j = 0; j < strs[i].length(); ++j) {
                if (strs[i].charAt(j) == '0') {
                    ++zeroes[i];
                } else {
                    ++ones[i];
                }
            }
        }

        int[][][] cache = new int[strs.length][m + 1][n + 1];
        for (int i = 0; i < strs.length; ++i) {
            for (int j = 0; j < m + 1; ++j) {
                Arrays.fill(cache[i][j], -1);
            }
        }

        return findMaxFormHelper(strs.length - 1, m, n, cache, zeroes, ones);
    }

    private int findMaxFormHelper(int i, int m, int n, int[][][] cache, int[] zeroes, int[] ones) {
        if (i < 0 || m < 0 || n < 0) {
            return 0;
        }

        int re = cache[i][m][n];
        if (re >= 0) {
            return re;
        }

        if (zeroes[i] > m || ones[i] > n) {
            re = findMaxFormHelper(i - 1, m, n, cache, zeroes, ones);
        } else {
            re = Math.max(findMaxFormHelper(i - 1, m, n, cache, zeroes, ones), 1 + findMaxFormHelper(i - 1, m - zeroes[i], n - ones[i], cache, zeroes, ones));
        }

        cache[i][m][n] = re;

        return re;
    }
}
