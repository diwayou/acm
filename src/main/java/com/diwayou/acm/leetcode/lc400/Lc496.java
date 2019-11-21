package com.diwayou.acm.leetcode.lc400;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;

/**
 * https://leetcode-cn.com/problems/next-greater-element-i/
 *
 * 给定两个没有重复元素的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
 * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出-1。
 *
 * 示例 1:
 * 输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * 输出: [-1,3,-1]
 * 解释:
 *     对于num1中的数字4，你无法在第二个数组中找到下一个更大的数字，因此输出 -1。
 *     对于num1中的数字1，第二个数组中数字1右边的下一个较大数字是 3。
 *     对于num1中的数字2，第二个数组中没有下一个更大的数字，因此输出 -1。
 *
 * 示例 2:
 * 输入: nums1 = [2,4], nums2 = [1,2,3,4].
 * 输出: [3,-1]
 * 解释:
 *     对于num1中的数字2，第二个数组中的下一个较大数字是3。
 *     对于num1中的数字4，第二个数组中没有下一个更大的数字，因此输出 -1。
 *
 * 注意:
 * nums1和nums2中所有元素是唯一的。
 * nums1和nums2 的数组大小都不超过1000。
 */
public class Lc496 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc496().nextGreaterElement(new int[]{4,1,2}, new int[]{1,3,4,2})));
    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Deque<Integer> stack = new ArrayDeque<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && nums2[i] > stack.getLast()) {
                map.put(stack.removeLast(), nums2[i]);
            }
            stack.offerLast(nums2[i]);
        }
        while (!stack.isEmpty()) {
            map.put(stack.removeLast(), -1);
        }

        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }

        return res;
    }
}