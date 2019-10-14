package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/circular-array-loop/
 * <p>
 * 给定一个含有正整数和负整数的环形数组 nums。 如果某个索引中的数 k 为正数，则向前移动 k 个索引。相反，如果是负数 (-k)，则向后移动 k 个索引。
 * 因为数组是环形的，所以可以假设最后一个元素的下一个元素是第一个元素，而第一个元素的前一个元素是最后一个元素。
 * 确定 nums 中是否存在循环（或周期）。循环必须在相同的索引处开始和结束并且循环长度 > 1。此外，一个循环中的所有运动都必须沿着同一方向进行。
 * 换句话说，一个循环中不能同时包括向前的运动和向后的运动。
 */
public class Lc457 {

    public static void main(String[] args) {
        System.out.println(new Lc457().circularArrayLoop1(new int[]{3, 1, 2}));
    }

    public boolean circularArrayLoop1(int[] nums) {
        int[] mark = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            if (mark[i] != 0) {
                continue;
            }

            int j = i;
            boolean forward = nums[j] > 0;
            do {
                mark[j] = i + 1;
                j = (j + nums[j]) % nums.length;
                if (j < 0) {
                    j += nums.length;
                }

                if ((nums[j] < 0 && forward) || (nums[j] > 0 && !forward)) {
                    break;
                }

                if (mark[j] == (i + 1)) {
                    int t = (j + nums[j]) % nums.length;
                    if (t < 0) {
                        t += nums.length;
                    }
                    if (t == j) {
                        break;
                    }

                    return true;
                }
            } while (mark[j] == 0);
        }

        return false;
    }

    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;

        if (n == 1) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                continue;
            }
            // slow/fast pointer
            int j = i, k = getIndex(i, nums);
            while (nums[k] * nums[i] > 0 && nums[getIndex(k, nums)] * nums[i] > 0) {
                if (j == k) {
                    // check for loop with only one element
                    if (j == getIndex(j, nums)) {
                        break;
                    }
                    return true;
                }
                j = getIndex(j, nums);
                k = getIndex(getIndex(k, nums), nums);
            }
            // loop not found, set all element along the way to 0
            j = i;
            int val = nums[i];
            while (nums[j] * val > 0) {
                int next = getIndex(j, nums);
                nums[j] = 0;
                j = next;
            }
        }

        return false;
    }

    public int getIndex(int i, int[] nums) {
        int n = nums.length;
        return i + nums[i] >= 0 ? (i + nums[i]) % n : n + ((i + nums[i]) % n);
    }
}
