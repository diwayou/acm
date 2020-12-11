package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/
 * <p>
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 示例1:
 * 给定 nums = [1,1,1,2,2,3],
 * 函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。
 * 你不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 示例2:
 * 给定 nums = [0,0,1,1,1,1,2,3,3],
 * 函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为0, 0, 1, 1, 2, 3, 3 。
 * 你不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 说明:
 * 为什么返回数值是整数，但输出的答案是数组呢?
 * 请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
 * 你可以想象内部操作如下:
 * // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
 * int len = removeDuplicates(nums);
 * // 在函数里修改输入数组对于调用者是可见的。
 * // 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
 * for (int i = 0; i < len; i++) {
 * print(nums[i]);
 * }
 */
public class Lc80 {

    public static void main(String[] args) {
        System.out.println(new Lc80().removeDuplicates(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3}));
    }

    public int removeDuplicates(int[] nums) {
        if (nums.length < 3) {
            return nums.length;
        }

        int cnt = 1, idx = 0, pre = nums[0];
        for (int i = 1; i <= nums.length; i++) {
            if (i == nums.length || nums[i] != pre) {
                nums[idx++] = pre;
                if (cnt > 1) {
                    nums[idx++] = pre;
                }

                if (i == nums.length) {
                    break;
                }

                pre = nums[i];
                cnt = 1;
            } else if (nums[i] == pre) {
                cnt++;
            }
        }

        return idx;
    }

    public int removeDuplicates1(int[] nums) {
        int p = 2;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] != nums[p - 2]) {
                nums[p++] = nums[i];
            }
        }

        return p;
    }
}
