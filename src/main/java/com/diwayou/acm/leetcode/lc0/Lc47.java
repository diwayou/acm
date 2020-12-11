package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutations-ii/
 * <p>
 * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
 */
public class Lc47 {

    public static void main(String[] args) {
        System.out.println(new Lc47().permuteUnique(new int[]{1, 1, 2}));
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> re = new ArrayList<>();
        permuteUniqueHelper(nums, 0, re);

        return re;
    }

    private void permuteUniqueHelper(int[] nums, int i, List<List<Integer>> re) {
        if (i == nums.length) {
            List<Integer> t = new ArrayList<>(nums.length);
            for (int n : nums) {
                t.add(n);
            }
            re.add(t);
            return;
        }

        outer:
        for (int j = i; j < nums.length; j++) {
            for (int k = i; k < j; k++) {
                if (nums[k] == nums[j]) {
                    continue outer;
                }
            }

            swap(nums, i, j);
            permuteUniqueHelper(nums, i + 1, re);
            swap(nums, i, j);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
