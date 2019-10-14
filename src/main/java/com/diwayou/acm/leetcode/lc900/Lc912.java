package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/sort-an-array/
 */
public class Lc912 {

    public int[] sortArray(int[] nums) {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        int[] buckets = new int[max - min + 1];
        for (int num : nums) {
            buckets[num - min]++;
        }

        int index = 0;
        int n = buckets.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < buckets[i]; j++) {
                nums[index++] = min + i;
            }
        }

        return nums;
    }
}
