package com.diwayou.acm.leetcode.lc200;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/missing-number/
 * <p>
 * 给定一个包含 0, 1, 2, ..., n中n个数的序列，找出 0 .. n中没有出现在序列中的那个数。
 * <p>
 * 示例 1:
 * 输入: [3,0,1]
 * 输出: 2
 * <p>
 * 示例2:
 * 输入: [9,6,4,2,3,5,7,0,1]
 * 输出: 8
 * <p>
 * 说明:
 * 你的算法应具有线性时间复杂度。你能否仅使用额外常数空间来实现?
 */
public class Lc268 {

    public static void main(String[] args) {
        System.out.println(new Lc268().missingNumber(new int[]{3, 2, 0}));
    }

    public int missingNumber(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                return i;
            }
        }

        return n;
    }

    public int missingNumber1(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        return nums.length * (nums.length + 1) / 2 - sum;
    }

    public int missingNumber2(int[] nums) {
        int ret = 0;
        for (int i = 0; i < nums.length; i++) {
            ret ^= (i ^ nums[i]);
        }

        return ret ^ nums.length;
    }
}
