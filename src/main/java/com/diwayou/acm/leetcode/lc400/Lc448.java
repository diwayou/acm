package com.diwayou.acm.leetcode.lc400;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array/
 *
 * 给定一个范围在 1 ≤ a[i] ≤ n (n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。
 * 找到所有在 [1, n] 范围之间没有出现在数组中的数字。
 * 您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内。
 *
 * 示例:
 * 输入:
 * [4,3,2,7,8,2,3,1]
 * 输出:
 * [5,6]
 */
public class Lc448 {

    public static void main(String[] args) {
        System.out.println(new Lc448().findDisappearedNumbers(new int[]{4,3,2,7,8,2,3,1}));
    }

    public List<Integer> findDisappearedNumbers1(int[] nums) {
        for (int i = 0; i < nums.length; ) {
            if (nums[i] == -1 || nums[i] == 0) {
                i++;
                continue;
            }

            if (nums[i] == i + 1) {
                nums[i] = -1;
                i++;
            } else {
                int t = nums[nums[i] - 1];
                nums[nums[i] - 1] = -1;
                if (t == -1) {
                    nums[i] = 0;
                } else {
                    nums[i] = t;
                }
            }
        }

        List<Integer> re = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != -1) {
                re.add(i + 1);
            }
        }

        return re;
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                list.add(i + 1);
            }
        }
        return list;

    }

    private void swap(int[] nums, int i, int j) {
        nums[i] = nums[j] + nums[i];
        nums[j] = nums[i] - nums[j];
        nums[i] = nums[i] - nums[j];
    }
}
