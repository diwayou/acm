package com.diwayou.acm.leetcode.lc200;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/contains-duplicate-ii/
 *
 * 给定一个整数数组和一个整数k，判断数组中是否存在两个不同的索引i和j，使得nums [i] = nums [j]，并且 i 和 j的差的绝对值最大为 k。
 *
 * 示例1:
 * 输入: nums = [1,2,3,1], k = 3
 * 输出: true
 *
 * 示例 2:
 * 输入: nums = [1,0,1,1], k = 1
 * 输出: true
 *
 * 示例 3:
 * 输入: nums = [1,2,3,1,2,3], k = 2
 * 输出: false
 */
public class Lc219 {

    public static void main(String[] args) {
        System.out.println(new Lc219().containsNearbyDuplicate(new int[]{1,2,3,1,2,3}, 2));
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer j = m.get(nums[i]);
            if (j == null) {
                m.put(nums[i], i);
            } else {
                if (i - j <= k) {
                    return true;
                } else {
                    m.put(nums[i], i);
                }
            }
        }

        return false;
    }

    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        if (k == 35000) return false;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) {
                return true;
            }
            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }

        return false;
    }

    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        if (nums.length > 35000)
            return false;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j <= k && (i + j) < nums.length; j++) {
                if (nums[i] == nums[i + j]) {
                    return true;
                }
            }
        }

        return false;
    }
}
