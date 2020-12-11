package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 * <p>
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 * 你的算法时间复杂度必须是O(log n) 级别。
 * 如果数组中不存在目标值，返回[-1, -1]。
 * <p>
 * 示例 1:
 * 输入: nums = [5,7,7,8,8,10], target = 8
 * 输出: [3,4]
 * <p>
 * 示例2:
 * 输入: nums = [5,7,7,8,8,10], target = 6
 * 输出: [-1,-1]
 */
public class Lc34 {

    public int[] searchRange(int[] nums, int target) {
        if (nums.length <= 0) {
            return new int[]{-1, -1};
        }

        int l = 0, r = nums.length - 1, m;
        while (l <= r) {
            m = (l + r) / 2;
            if (nums[m] > target) {
                r = m - 1;
            } else if (nums[m] < target) {
                l = m + 1;
            } else {
                l = r = m;
                while (l > 0 && nums[l - 1] == target) {
                    l--;
                }
                while (r < nums.length - 1 && nums[r + 1] == target) {
                    r++;
                }

                return new int[]{l, r};
            }
        }

        return new int[]{-1, -1};
    }
}
