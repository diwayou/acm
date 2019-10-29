package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutations/
 *
 * 给定一个没有重复数字的序列，返回其所有可能的全排列。
 */
public class Lc46 {

    public static void main(String[] args) {
        System.out.println(new Lc46().permute(new int[]{1, 2, 3}));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        permuteHelper(nums, 0, result);

        return result;
    }

    private void permuteHelper(int[] nums, int i, List<List<Integer>> result) {
        if (i == nums.length) {
            List<Integer> l = new ArrayList<>(nums.length);
            for (int k = 0; k < nums.length; k++) {
                l.add(nums[k]);
            }
            result.add(l);
        }

        for (int j = i; j < nums.length; j++) {
            swap(nums, i, j);
            permuteHelper(nums, i + 1, result);
            swap(nums, i, j);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
