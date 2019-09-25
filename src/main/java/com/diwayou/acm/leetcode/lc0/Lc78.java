package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/subsets/
 */
public class Lc78 {

    public static void main(String[] args) {
        System.out.println(new Lc78().subsets(new int[]{1, 2, 3}));
    }

    public List<List<Integer>> subsets(int[] nums) {
        if (nums.length == 0) {
            return Collections.emptyList();
        }

        List<List<Integer>> result = new ArrayList<>();

        subsetsHelper(nums, new ArrayList<>(nums.length), 0, result);

        return result;
    }

    private void subsetsHelper(int[] nums, List<Integer> backtrack, int i, List<List<Integer>> result) {
        result.add(new ArrayList<>(backtrack));

        for (int j = i; j < nums.length; j++) {
            backtrack.add(nums[j]);
            subsetsHelper(nums, backtrack, j + 1, result);
            backtrack.remove(backtrack.size() - 1);
        }
    }
}
