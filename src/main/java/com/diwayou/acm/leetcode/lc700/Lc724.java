package com.diwayou.acm.leetcode.lc700;

/**
 * https://leetcode-cn.com/problems/find-pivot-index/
 *
 * 给定一个整数类型的数组 nums，请编写一个能够返回数组“中心索引”的方法。
 * 我们是这样定义数组中心索引的：数组中心索引的左侧所有元素相加的和等于右侧所有元素相加的和。
 * 如果数组不存在中心索引，那么我们应该返回 -1。如果数组有多个中心索引，那么我们应该返回最靠近左边的那一个。
 *
 * 示例 1:
 * 输入:
 * nums = [1, 7, 3, 6, 5, 6]
 * 输出: 3
 * 解释:
 * 索引3 (nums[3] = 6) 的左侧数之和(1 + 7 + 3 = 11)，与右侧数之和(5 + 6 = 11)相等。
 * 同时, 3 也是第一个符合要求的中心索引。
 *
 * 示例 2:
 * 输入:
 * nums = [1, 2, 3]
 * 输出: -1
 * 解释:
 * 数组中不存在满足此条件的中心索引。
 *
 * 说明:
 * nums 的长度范围为 [0, 10000]。
 * 任何一个 nums[i] 将会是一个范围在 [-1000, 1000]的整数。
 */
public class Lc724 {

    public static void main(String[] args) {
        System.out.println(new Lc724().pivotIndex(new int[]{1, 2,3}));
    }

    public int pivotIndex(int[] nums) {
        if (nums.length < 3) {
            return -1;
        }

        int l = 0, r = 0;
        for (int i = 1; i < nums.length; i++) {
            r += nums[i];
        }
        int i;
        for (i = 0; i < nums.length - 1; i++) {
            if (l == r) {
                break;
            }

            l += nums[i];
            r -= nums[i + 1];
        }

        return l == r ? i : -1;
    }

    public int pivotIndex1(int[] nums) {
        int sum = 0, leftSum = 0;

        for (int num : nums) {
            sum += num;
        }

        for (int i = 0; i < nums.length; i++) {
            if (sum - nums[i] == leftSum * 2) {
                return i;
            } else {
                leftSum += nums[i];
            }
        }

        return -1;
    }
}
