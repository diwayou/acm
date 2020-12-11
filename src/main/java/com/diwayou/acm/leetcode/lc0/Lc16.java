package com.diwayou.acm.leetcode.lc0;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/3sum-closest/
 * <p>
 * 给定一个包括n 个整数的数组nums和 一个目标值target。找出nums中的三个整数，使得它们的和与target最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 * 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
 * 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
 */
public class Lc16 {

    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 8, 16, 32, 64, 128};
        int target = 82;
        System.out.println(new Lc16().threeSumClosest(nums, target));
    }

    public int threeSumClosest(int[] nums, int target) {
        if (nums.length < 3) {
            return 0;
        }

        Arrays.sort(nums);
        int closest = Integer.MAX_VALUE, min = Integer.MAX_VALUE;
        for (int k = 0; k < nums.length - 2; k++) {
            if (nums[k] > closest) {
                break;
            }

            if (k > 0 && nums[k] == nums[k - 1]) {
                continue;
            }

            int i = k + 1, j = nums.length - 1;
            while (i < j) {
                int sum = nums[k] + nums[i] + nums[j];
                int v = sum - target;
                if (Math.abs(v) < min) {
                    closest = sum;
                    min = Math.abs(v);
                }

                if (v == 0) {
                    return sum;
                } else if (v > 0) {
                    while (i < j && nums[j] == nums[--j])
                        ;
                } else {
                    while (i < j && nums[i] == nums[++i])
                        ;
                }
            }
        }

        return closest;
    }
}
