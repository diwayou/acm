package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/merge-sorted-array/
 */
public class Lc88 {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) {
            return;
        }

        int j = m - 1, k = n - 1;
        for (int i = m + n - 1; i >= 0; i--) {
            if (j < 0) {
                nums1[i] = nums2[k--];
                continue;
            }
            if (k < 0) {
                break;
            }

            if (nums1[j] >= nums2[k]) {
                nums1[i] = nums1[j--];
            } else {
                nums1[i] = nums2[k--];
            }
        }
    }
}
