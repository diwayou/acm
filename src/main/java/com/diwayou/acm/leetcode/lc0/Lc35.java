package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/search-insert-position/
 */
public class Lc35 {

    public int searchInsert(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }

        int l = 0, r = nums.length - 1, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (nums[mid] > target) {
                r = mid - 1;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                return mid;
            }
        }

        return l;
    }
}
