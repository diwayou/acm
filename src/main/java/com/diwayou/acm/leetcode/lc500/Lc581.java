package com.diwayou.acm.leetcode.lc500;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/
 * <p>
 * 给定一个整数数组，你需要寻找一个连续的子数组，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * <p>
 * 你找到的子数组应是最短的，请输出它的长度。
 */
public class Lc581 {

    public static void main(String[] args) {
        System.out.println(new Lc581().findUnsortedSubarray(new int[]{3, 2, 1}));
    }

    public int findUnsortedSubarray(int[] nums) {
        if (nums.length <= 1) {
            return 0;
        }

        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);
        int cnt = 0;
        int i;
        for (i = 0; i < nums.length; i++) {
            if (nums[i] == copy[i]) {
                cnt++;
            } else {
                break;
            }
        }
        for (int j = nums.length - 1; j > i; j--) {
            if (nums[j] == copy[j]) {
                cnt++;
            } else {
                break;
            }
        }

        return nums.length - cnt;
    }

    public int findUnsortedSubarray1(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi && nums[lo] <= nums[lo + 1]) {
            lo++;
        }
        while (lo < hi && nums[hi - 1] <= nums[hi]) {
            hi--;
        }
        if (lo == hi)
            return 0;
        //说明lo的右边和hi的左边开始无序了
        //但是这个不一定是结果，因为如果里面最大的比边界大的话，边界也是要外扩的
        //记录里面最大和最小的
        int max = nums[lo], min = nums[lo];
        for (int i = lo; i <= hi; i++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }
        //然后一步步往外扩
        while (lo >= 0 && nums[lo] > min) {
            lo--;
        }
        while (hi < nums.length && nums[hi] < max) {
            hi++;
        }
        //无序的是下标为lo+1,hi-1之间的数据
        return hi - lo - 1;
    }
}
