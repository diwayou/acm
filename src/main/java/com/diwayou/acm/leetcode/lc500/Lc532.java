package com.diwayou.acm.leetcode.lc500;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/k-diff-pairs-in-an-array/
 *
 * 给定一个整数数组和一个整数k, 你需要在数组里找到不同的k-diff 数对。这里将k-diff数对定义为一个整数对 (i, j), 其中 i 和 j 都是数组中的数字，且两数之差的绝对值是k.
 *
 * 示例 1:
 * 输入: [3, 1, 4, 1, 5], k = 2
 * 输出: 2
 * 解释: 数组中有两个 2-diff 数对, (1, 3) 和 (3, 5)。
 * 尽管数组中有两个1，但我们只应返回不同的数对的数量。
 *
 * 示例2:
 * 输入:[1, 2, 3, 4, 5], k = 1
 * 输出: 4
 * 解释: 数组中有四个 1-diff 数对, (1, 2), (2, 3), (3, 4) 和 (4, 5)。
 *
 * 示例 3:
 * 输入: [1, 3, 1, 5, 4], k = 0
 * 输出: 1
 * 解释: 数组中只有一个 0-diff 数对，(1, 1)。
 *
 * 注意:
 * 数对 (i, j) 和数对(j, i) 被算作同一数对。
 * 数组的长度不超过10,000。
 * 所有输入的整数的范围在[-1e7, 1e7]。
 */
public class Lc532 {

    public static void main(String[] args) {
        System.out.println(new Lc532().findPairs(new int[]{1, 3, 1, 5, 4}, 1));
    }

    public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length < 2 || k < 0) {
            return 0;
        }

        Arrays.sort(nums);

        int re = 0, v;
        for (int i = 0; i < nums.length - 1; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int j;
            for (j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                v = nums[j] - nums[i];
                if (v == k) {
                    re++;
                    break;
                } else if (v > k) {
                    break;
                }
            }

            if (j == nums.length) {
                return re;
            }
        }

        return re;
    }

    public int findPairs1(int[] nums, int k) {
        if (nums == null || nums.length < 2 || k < 0) {
            return 0;
        }

        Arrays.sort(nums);

        int re = 0;
        int l = 0;
        int r = 1;
        while (r < nums.length) {
            if (nums[r] - nums[l] == k) {
                re++;
                r++;
                while (r < nums.length && nums[r] == nums[r - 1]) {
                    r++;
                }
                while (l < nums.length - 1 && nums[l] == nums[l + 1]) {
                    l++;
                }
            } else if (nums[r] - nums[l] < k) {
                r++;
            } else if (nums[r] - nums[l] > k) {
                l++;
            }

            if (l == r) {
                r++;
            }
        }

        return re;
    }

    public int findPairs2(int[] nums, int k) {
        if (k < 0) {
            return 0;
        }

        HashMap<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            m.put(nums[i], m.getOrDefault(nums[i], 0) + 1);
        }

        int sum = 0;
        for (Map.Entry<Integer, Integer> e : m.entrySet()) {
            if (k == 0) {
                if (e.getValue() > 1) {
                    sum++;
                }
            } else {
                if (m.containsKey(e.getKey() + k)) {
                    sum++;
                }
            }
        }

        return sum;
    }
}
