package com.diwayou.acm.leetcode.lc500;

import java.util.Arrays;
import java.util.HashMap;

/**
 * https://leetcode-cn.com/problems/longest-harmonious-subsequence/
 * <p>
 * 和谐数组是指一个数组里元素的最大值和最小值之间的差别正好是1。
 * 现在，给定一个整数数组，你需要在所有可能的子序列中找到最长的和谐子序列的长度。
 * <p>
 * 示例 1:
 * 输入: [1,3,2,2,5,2,3,7]
 * 输出: 5
 * 原因: 最长的和谐数组是：[3,2,2,2,3].
 * 说明: 输入的数组长度最大不超过20,000.
 */
public class Lc594 {

    public int findLHS(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            boolean flag = false;
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] == nums[i])
                    count++;
                else if (nums[j] + 1 == nums[i]) {
                    count++;
                    flag = true;
                }
            }

            if (flag)
                res = Math.max(count, res);
        }

        return res;
    }

    public int findLHS1(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int key : map.keySet()) {
            if (map.containsKey(key + 1))
                res = Math.max(res, map.get(key) + map.get(key + 1));
        }

        return res;
    }

    public int findLHS2(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.containsKey(num + 1))
                res = Math.max(res, map.get(num) + map.get(num + 1));
            if (map.containsKey(num - 1))
                res = Math.max(res, map.get(num) + map.get(num - 1));
        }

        return res;
    }

    public int findLHS3(int[] nums) {
        Arrays.sort(nums);

        int start = 0;
        int end = 0;
        int max = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                end++;
            } else if (nums[i + 1] - nums[i] == 1) {
                if (i + 1 == nums.length - 1) {
                    end++;
                    max = Math.max(max, end - start + 1);
                } else {
                    int tmp = i + 1;
                    end++;
                    i++;
                    while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                        end++;
                        i++;
                    }
                    max = Math.max(max, end - start + 1);
                    start = tmp;
                    i--;
                }
            } else {
                start = i + 1;
                end = i + 1;
            }
        }

        return max;
    }
}
