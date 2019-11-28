package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/subsets-ii/
 *
 * 给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 *
 * 说明：解集不能包含重复的子集。
 *
 * 示例:
 * 输入: [1,2,2]
 * 输出:
 * [
 *   [2],
 *   [1],
 *   [1,2,2],
 *   [2,2],
 *   [1,2],
 *   []
 * ]
 */
public class Lc90 {

    public static void main(String[] args) {
        System.out.println(new Lc90().subsetsWithDup(new int[]{}));
    }

    private List<List<Integer>> re = new ArrayList<>();

    private int[] backtrack;

    private int idx;

    private int len;

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums.length == 0) {
            return Collections.singletonList(Collections.emptyList());
        }

        len = nums.length;
        backtrack = new int[len];

        Arrays.sort(nums);
        subsetsWithDupHelper(nums, 0);

        return re;
    }

    private void subsetsWithDupHelper(int[] nums, int i) {
        List<Integer> t = new ArrayList<>(idx);
        for (int j = 0; j < idx; j++) {
            t.add(backtrack[j]);
        }
        re.add(t);

        for (int k = i; k < len; k++) {
            if (k > i && nums[k] == nums[k - 1]) {
                continue;
            }

            backtrack[idx++] = nums[k];
            subsetsWithDupHelper(nums, k + 1);
            idx--;
        }
    }
}
