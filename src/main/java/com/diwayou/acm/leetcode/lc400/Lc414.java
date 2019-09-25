package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/third-maximum-number/
 */
public class Lc414 {
    public static void main(String[] args) {
        System.out.println(new Lc414().thirdMax(new int[]{1, 2, Integer.MIN_VALUE}));
    }

    public int thirdMax(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int first = Integer.MIN_VALUE, second = Integer.MIN_VALUE, third = Integer.MIN_VALUE;
        int cur;
        boolean flag = false;
        for (int i = 0; i < nums.length; i++) {
            cur = nums[i];

            if (cur == Integer.MIN_VALUE) {
                flag = true;
            }
            if (cur <= third || cur == first || cur == second) {
                continue;
            }

            if (cur > first) {
                third = second;
                second = first;
                first = cur;
            } else if (cur > second) {
                third = second;
                second = cur;
            } else if (cur > third) {
                third = cur;
            }
        }

        if (third > Integer.MIN_VALUE)
            return third;

        if (second > Integer.MIN_VALUE && flag)
            return Integer.MIN_VALUE;

        return first;
    }
}
