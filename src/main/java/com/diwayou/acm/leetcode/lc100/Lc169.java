package com.diwayou.acm.leetcode.lc100;

/**
 * https://leetcode-cn.com/problems/majority-element/
 */
public class Lc169 {

    public static void main(String[] args) {
        System.out.println(new Lc169().majorityElement(new int[] {2,2,1,1,1,2,2}));
    }

    public int majorityElement(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int v = nums[0];
        int cnt = 1;
        for (int i = 1; i < nums.length; i++) {
            if (v != nums[i]) {
                if (cnt == 1) {
                    v = nums[i];
                } else {
                    cnt--;
                }
            } else {
                cnt++;
            }
        }

        return v;
    }
}
