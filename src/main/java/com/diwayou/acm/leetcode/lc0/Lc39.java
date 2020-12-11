package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/combination-sum/
 * <p>
 * 给定一个无重复元素的数组candidates和一个目标数target，找出candidates中所有可以使数字和为target的组合。
 * candidates中的数字可以无限制重复被选取。
 * <p>
 * 说明：
 * 所有数字（包括target）都是正整数。
 * 解集不能包含重复的组合。
 * <p>
 * 示例1:
 * 输入: candidates = [2,3,6,7], target = 7,
 * 所求解集为:
 * [
 * [7],
 * [2,2,3]
 * ]
 * <p>
 * 示例2:
 * 输入: candidates = [2,3,5], target = 8,
 * 所求解集为:
 * [
 * [2,2,2,2],
 * [2,3,3],
 * [3,5]
 * ]
 */
public class Lc39 {

    public static void main(String[] args) {
        int[] candidates = new int[]{2, 3, 6, 7};
        int target = 7;
        System.out.println(new Lc39().combinationSum(candidates, target));
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> re = new ArrayList<>();
        List<Integer> backtrack = new ArrayList<>(candidates.length);

        combinationSumHelper(candidates, 0, 0, backtrack, re, target);

        return re;
    }

    private void combinationSumHelper(int[] candidates, int i, int cur, List<Integer> backtrack, List<List<Integer>> re, int target) {
        if (cur == target) {
            re.add(new ArrayList<>(backtrack));
            return;
        }
        if (cur > target) {
            return;
        }

        for (int j = i; j < candidates.length; j++) {
            backtrack.add(candidates[j]);
            combinationSumHelper(candidates, j, cur + candidates[j], backtrack, re, target);
            backtrack.remove(backtrack.size() - 1);
        }
    }
}
