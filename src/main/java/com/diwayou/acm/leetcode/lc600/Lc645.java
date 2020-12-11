package com.diwayou.acm.leetcode.lc600;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/set-mismatch/
 * <p>
 * 集合 S 包含从1到n的整数。不幸的是，因为数据错误，导致集合里面某一个元素复制了成了集合里面的另外一个元素的值，导致集合丢失了一个整数并且有一个元素重复。
 * 给定一个数组 nums 代表了集合 S 发生错误后的结果。你的任务是首先寻找到重复出现的整数，再找到丢失的整数，将它们以数组的形式返回。
 * <p>
 * 示例 1:
 * 输入: nums = [1,2,2,4]
 * 输出: [2,3]
 * <p>
 * 注意:
 * 给定数组的长度范围是[2, 10000]。
 * 给定的数组是无序的。
 */
public class Lc645 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc645().findErrorNums(new int[]{2, 3, 2})));
    }

    public int[] findErrorNums(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                int t = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = t;
            }
        }

        int[] re = null;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                re = new int[]{nums[i], i + 1};
                break;
            }
        }

        return re;
    }
}
