package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/combinations/
 *
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 */
public class Lc77 {
    public static void main(String[] args) {
        System.out.println(new Lc77().combine(4, 2));
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combineHelper(n, 1, k, new ArrayList<>(k), result);

        return result;
    }

    private void combineHelper(int n, int i, int k, ArrayList<Integer> backtrack, List<List<Integer>> result) {
        if (0 == k) {
            result.add(new ArrayList<>(backtrack));
            return;
        }

        for (int j = i; j <= n - k + 1; j++) {
            backtrack.add(j);
            combineHelper(n, j + 1, k - 1, backtrack, result);
            backtrack.remove(backtrack.size() - 1);
        }
    }
}
