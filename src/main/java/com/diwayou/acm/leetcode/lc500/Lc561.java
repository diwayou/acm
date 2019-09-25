package com.diwayou.acm.leetcode.lc500;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/array-partition-i/
 */
public class Lc561 {

    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length; i += 2) {
            sum += nums[i];
        }

        return sum;
    }

    public int arrayPairSum1(int[] nums) {
        int[] cnts = new int[20001];
        for (int i = 0; i < nums.length; ++i)
            ++cnts[nums[i] + 10000];

        int sum = 0;
        boolean isOdd = true;
        for (int i = 0; i < cnts.length; ++i) {
            if (cnts[i] != 0) {
                if (isOdd) sum += i - 10000;
                isOdd = !isOdd;
                --cnts[i--];
            }
        }

        return sum;
    }
}
