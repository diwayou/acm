package com.diwayou.acm.leetcode.lc500;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/next-greater-element-ii/
 * <p>
 * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，
 * 这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
 */
public class Lc503 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc503().nextGreaterElements(new int[]{1, 2, 1})));
    }

    public int[] nextGreaterElements1(int[] nums) {
        if (nums.length <= 0) {
            return nums;
        }

        if (nums.length == 1) {
            nums[0] = -1;
            return nums;
        }

        int[] re = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int j = (i + 1) % nums.length;
            while (j != i) {
                if (nums[j] > nums[i]) {
                    re[i] = nums[j];
                    break;
                }

                j = (j + 1) % nums.length;
            }

            if (j == i) {
                re[i] = -1;
            }
        }

        return re;
    }

    public int[] nextGreaterElements(int[] nums) {
        if (nums.length <= 0) {
            return nums;
        }

        if (nums.length == 1) {
            nums[0] = -1;
            return nums;
        }

        int[] result = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            boolean found = false;

            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    result[i] = nums[j];
                    found = true;
                    break;
                } else if (nums[i] < result[j]) {
                    result[i] = result[j];
                    found = true;
                    break;
                }

            }

            if (!found) {
                for (int j = 0; j < i; j++) {
                    if (nums[i] < nums[j]) {
                        found = true;
                        result[i] = nums[j];
                        break;
                    }
                }
            }

            if (!found) {
                result[i] = -1;
            }
        }

        return result;
    }
}
