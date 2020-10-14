package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/remove-element/
 *
 * 给定一个数组 nums和一个值 val，你需要原地移除所有数值等于val的元素，返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 */
public class Lc27 {

    public int removeElement(int[] nums, int val) {
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                continue;
            }

            nums[idx++] = nums[i];
        }

        return idx;
    }
}
