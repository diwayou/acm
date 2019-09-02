package com.diwayou.acm.team.bag01;

public class OnesAndZeroes1 {
    public static void main(String[] args) {
        String[] strs = new String[]{"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;

        System.out.println(new OnesAndZeroes1().findMaxForm(strs, m, n));
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

        return findMaxFormHelper(strs.length - 1, m, n, zeroes, ones);
    }

    private int findMaxFormHelper(int i, int m, int n, int[] zeroes, int[] ones) {
        if (i < 0 || m < 0 || n < 0) {
            return 0;
        }

        if (zeroes[i] > m || ones[i] > n) {
            return findMaxFormHelper(i - 1, m, n, zeroes, ones);
        } else {
            return Math.max(findMaxFormHelper(i - 1, m, n, zeroes, ones), 1 + findMaxFormHelper(i - 1, m - zeroes[i], n - ones[i], zeroes, ones));
        }
    }
}
