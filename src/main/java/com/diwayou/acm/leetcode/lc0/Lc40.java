package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/combination-sum-ii/
 * <p>
 * 给定一个数组candidates和一个目标数target，找出candidates中所有可以使数字和为target的组合。
 * candidates中的每个数字在每个组合中只能使用一次。
 * 说明：
 * 所有数字（包括目标数）都是正整数。
 * 解集不能包含重复的组合。
 * <p>
 * 示例1:
 * 输入: candidates =[10,1,2,7,6,1,5], target =8,
 * 所求解集为:
 * [
 * [1, 7],
 * [1, 2, 5],
 * [2, 6],
 * [1, 1, 6]
 * ]
 * <p>
 * 示例2:
 * 输入: candidates =[2,5,2,1,2], target =5,
 * 所求解集为:
 * [
 * [1,2,2],
 * [5]
 * ]
 */
public class Lc40 {

    public static void main(String[] args) {
        int[] candicates = new int[]{2, 5, 2, 1, 2};
        int target = 5;

        System.out.println(new Lc40().combinationSum2(candicates, target));
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> re = new ArrayList<>();
        List<Integer> backtrack = new ArrayList<>(candidates.length);

        Arrays.sort(candidates);
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
            if (cur + candidates[j] > target) {
                break;
            }

            if (j > i && candidates[j] == candidates[j - 1]) {
                continue;
            }

            backtrack.add(candidates[j]);
            combinationSumHelper(candidates, j + 1, cur + candidates[j], backtrack, re, target);
            backtrack.remove(backtrack.size() - 1);
        }
    }
}
