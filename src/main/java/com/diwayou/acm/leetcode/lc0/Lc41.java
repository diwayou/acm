package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/first-missing-positive/
 * <p>
 * 给定一个未排序的整数数组，找出其中没有出现的最小的正整数。
 * <p>
 * 示例1:
 * 输入: [1,2,0]
 * 输出: 3
 * <p>
 * 示例2:
 * 输入: [3,4,-1,1]
 * 输出: 2
 * <p>
 * 示例3:
 * 输入: [7,8,9,11,12]
 * 输出: 1
 * 说明:
 * <p>
 * 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的空间。
 */
public class Lc41 {

    public static void main(String[] args) {
        System.out.println(new Lc41().firstMissingPositive(new int[]{1, 1}));
    }

    public int firstMissingPositive(int[] nums) {
        int t;
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] <= nums.length && nums[i] > 0 && nums[i] != i + 1) {
                t = nums[nums[i] - 1];
                if (t == nums[i]) {
                    break;
                }

                nums[nums[i] - 1] = nums[i];
                nums[i] = t;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return nums.length + 1;
    }
}
