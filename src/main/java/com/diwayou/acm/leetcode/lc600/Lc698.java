package com.diwayou.acm.leetcode.lc600;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/partition-to-k-equal-sum-subsets/
 *
 * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
 *
 * 示例 1：
 * 输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * 输出： True
 * 说明： 有可能将其分成 4 个子集（5），（1,4），（2,3），（2,3）等于总和。
 *
 * 注意:
 * 1 <= k <= len(nums) <= 16
 * 0 < nums[i] < 10000
 */
public class Lc698 {

    public static void main(String[] args) {
        System.out.println(new Lc698().canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4));
        System.out.println(new Lc698().canPartitionKSubsets(new int[]{10,10,10,7,7,7,7,7,7,6,6,6}, 3));
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum % k != 0) {
            return false;
        }

        int size = sum / k;
        if (k == nums.length) {
            for (int num : nums) {
                if (num != size) {
                    return false;
                }
            }

            return true;
        } else {
            int[] cache = new int[1 << nums.length];
            cache[cache.length - 1] = 1;

            return search(0, sum, cache, nums, size);
        }
    }

    private boolean search(int used, int sum, int[] cache, int[] nums, int size) {
        if (cache[used] == 0) {
            cache[used] = -1;
            int targ = (sum - 1) % size + 1;
            for (int i = 0; i < nums.length; i++) {
                if ((((used >> i) & 1) == 0) && nums[i] <= targ) {
                    if (search(used | (1 << i), sum - nums[i], cache, nums, size)) {
                        cache[used] = 1;
                        break;
                    }
                }
            }
        }

        return cache[used] == 1;
    }

    public boolean canPartitionKSubsets1(int[] nums, int k) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        if (sum % k != 0) {
            return false;
        }
        int target = sum / k;

        Arrays.sort(nums);
        int row = nums.length - 1;
        if (nums[row] > target) {
            return false;
        }
        int[] groups = new int[k];
        return search(groups, row, nums, target);
    }

    boolean search(int[] group, int row, int[] nums, int target) {
        if (row < 0) {
            return true;
        }
        int val = nums[row];
        for (int i = 0; i < group.length; i++) {
            if (group[i] + val <= target) {
                group[i] += val;
                if (search(group, row - 1, nums, target)) return true;
                group[i] -= val;
            }
            if (group[i] == 0) {
                return false;
            }
        }

        return false;
    }
}
