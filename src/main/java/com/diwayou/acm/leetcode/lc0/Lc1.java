package com.diwayou.acm.leetcode.lc0;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/two-sum/
 */
public class Lc1 {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> index = new HashMap<>();

        index.put(nums[0], 0);

        Integer v;
        for (int i = 1; i < nums.length; i++) {
            v = index.get(target - nums[i]);
            if (v != null) {
                return new int[]{v, i};
            } else {
                index.put(nums[i], i);
            }
        }

        return null;
    }
}
