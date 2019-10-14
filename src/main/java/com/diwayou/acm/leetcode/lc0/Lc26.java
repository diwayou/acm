package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/
 */
public class Lc26 {

    public int removeDuplicates(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }

        int idx = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[idx++] = nums[i];
            }
        }

        return idx;
    }
}
