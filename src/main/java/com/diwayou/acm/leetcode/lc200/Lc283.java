package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/move-zeroes/
 * <p>
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * 示例:
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 说明:
 * <p>
 * 必须在原数组上操作，不能拷贝额外的数组。
 * 尽量减少操作次数。
 */
public class Lc283 {

    public static void main(String[] args) {
        new Lc283().moveZeroes(new int[]{0, 1, 0, 3, 12});
    }

    public void moveZeroes1(int[] nums) {
        if (nums.length < 2) {
            return;
        }

        int j = nums.length - 1, t;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == 0) {
                if (i == j) {
                    j--;
                    continue;
                }

                for (int k = i; k < j; k++) {
                    nums[k] = nums[k + 1];
                }
                nums[j] = 0;
            }
        }
    }

    public void moveZeroes(int[] nums) {
        int nozeroIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != nozeroIndex) {
                    nums[nozeroIndex] = nums[i];
                    nums[i] = 0;
                }
                nozeroIndex++;
            }
        }
    }
}
