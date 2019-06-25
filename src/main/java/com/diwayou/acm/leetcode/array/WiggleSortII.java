package com.diwayou.acm.leetcode.array;

// Given an unsorted array nums, reorder it in-place such that nums[0] < nums[1] > nums[2] < nums[3]....
// 给定一个无序的数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。

//示例 1:
//
//输入: nums = [1, 5, 1, 1, 6, 4]
//输出: 一个可能的答案是 [1, 4, 1, 5, 1, 6]
//示例 2:
//
//输入: nums = [1, 3, 2, 2, 3, 1]
//输出: 一个可能的答案是 [2, 3, 1, 3, 1, 2]
//
//来源：力扣（LeetCode）
//链接：https://leetcode-cn.com/problems/wiggle-sort-ii

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class WiggleSortII {

    public static void main(String[] args) {
        int[] nums = IntStream.range(0, 100).toArray();
        shuffle(nums);

        wiggleSort(nums);

        System.out.println(Arrays.toString(nums));
    }

    private static void shuffle(int[] nums) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = nums.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = nums[index];
            nums[index] = nums[i];
            nums[i] = a;
        }
    }

    public static void wiggleSort(int[] nums) {
        int n = nums.length;
        int indexMapSize = n | 1;

        // Find a median.
        int mid = nth_element(nums, n / 2);

        int i = 0, j = 0, k = n - 1;
        while (j <= k) {
            int cur = nums[indexMap(indexMapSize, j)];
            if (cur > mid) {
                System.out.print(String.format("i%d -- j%d   ", i, j));
                swap(nums, indexMap(indexMapSize, i++), indexMap(indexMapSize, j++));
            }
            else if (cur < mid) {
                System.out.print(String.format("j%d -- k%d   ", j, k));
                swap(nums, indexMap(indexMapSize, j), indexMap(indexMapSize, k--));
            }
            else
                j++;
        }
    }

    // Index-rewiring.
    private static int indexMap(int indexMapSize, int i) {
        return (1 + 2 * (i)) % indexMapSize;
    }

    private static void swap(int[] nums, int i, int j) {
        if (i == j) {
            System.out.println();
            return;
        }

        System.out.println(String.format("%d[%d] <-> %d[%d]", i, nums[i], j, nums[j]));
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    static void nth_element_helper2(int[] arr, int beg, int end) {
        for (int i = beg + 1; i < end; i++) {
            for (int j = i; j > beg; j--) {
                if (arr[j - 1] < arr[j])
                    break;
                int t = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = t;
            }
        }
    }

    static void nth_element_helper(int[] arr, int beg, int end, int index) {
        if (beg + 4 >= end) {
            nth_element_helper2(arr, beg, end);
            return;
        }
        int initial_beg = beg;
        int initial_end = end;

        // Pick a pivot (using the median of 3 technique)
        int pivA = arr[beg];
        int pivB = arr[(beg + end) / 2];
        int pivC = arr[end - 1];
        int pivot;
        if (pivA < pivB) {
            if (pivB < pivC)
                pivot = pivB;
            else if (pivA < pivC)
                pivot = pivC;
            else
                pivot = pivA;
        } else {
            if (pivA < pivC)
                pivot = pivA;
            else if (pivB < pivC)
                pivot = pivC;
            else
                pivot = pivB;
        }

        // Divide the values about the pivot
        while (true) {
            while (beg + 1 < end && arr[beg] < pivot)
                beg++;
            while (end > beg + 1 && arr[end - 1] > pivot)
                end--;
            if (beg + 1 >= end)
                break;

            // Swap values
            int t = arr[beg];
            arr[beg] = arr[end - 1];
            arr[end - 1] = t;

            beg++;
            end--;
        }
        if (arr[beg] < pivot)
            beg++;

        // Recurse
        if (beg == initial_beg || end == initial_end)
            throw new RuntimeException("No progress. Bad pivot");
        if (index < beg)
            nth_element_helper(arr, initial_beg, beg, index);
        else
            nth_element_helper(arr, beg, initial_end, index);
    }

    static int nth_element(int[] arr, int index) {
        nth_element_helper(arr, 0, arr.length, index);
        return arr[index];
    }
}
