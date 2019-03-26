package com.diwayou.acm.leetcode.array;

// Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
// 给定一个未排序的整数数组，找到最长的连续序列

// For example,
// Given [100, 4, 200, 1, 3, 2],
// The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

// Your algorithm should run in O(n) complexity.
// 需要是O(n)复杂度

import java.util.HashMap;

public class LongestConsecutiveSequence {

    public static int longestConsecutive(int[] nums) {
        int res = 0;

        // 当序列是稀疏的时候，需要想到是否可以用map来处理
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int n : nums) {
            // 数字重复的场景，需注意
            if (map.containsKey(n)) {
                continue;
            }

            int left = map.getOrDefault(n - 1, 0);
            int right = map.getOrDefault(n + 1, 0);

            int sum = left + right + 1;
            map.put(n, sum);
            res = Math.max(res, sum);

            // 比较关键，因为连续是双向的，所以当找到更长的序列的时候，需要更新对应的两边数值
            map.put(n - left, sum);
            map.put(n + right, sum);
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }
}
