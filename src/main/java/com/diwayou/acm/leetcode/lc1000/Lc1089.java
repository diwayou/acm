package com.diwayou.acm.leetcode.lc1000;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/duplicate-zeros/
 * <p>
 * 给你一个长度固定的整数数组arr，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
 * 注意：请不要在超过该数组长度的位置写入元素。
 * 要求：请对输入的数组就地进行上述修改，不要从函数返回任何东西。
 * <p>
 * 示例 1：
 * 输入：[1,0,2,3,0,4,5,0]
 * 输出：null
 * 解释：调用函数后，输入的数组将被修改为：[1,0,0,2,3,0,0,4]
 * <p>
 * 示例 2：
 * 输入：[1,2,3]
 * 输出：null
 * 解释：调用函数后，输入的数组将被修改为：[1,2,3]
 * <p>
 * 提示：
 * 1 <= arr.length <= 10000
 * 0 <= arr[i] <= 9
 */
public class Lc1089 {

    public static void main(String[] args) {
        int[] arr = new int[]{8, 4, 5, 0, 0, 0, 0, 7};
        new Lc1089().duplicateZeros(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void duplicateZeros(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                for (int j = arr.length - 1; j > i; j--) {
                    arr[j] = arr[j - 1];
                }
                i++;
            }
        }
    }

    public void duplicateZeros1(int[] arr) {
        int[] arr1 = Arrays.copyOf(arr, arr.length);
        for (int i = 0, j = 0; i < arr.length; i++, j++) {
            if (arr1[j] == 0 && i < arr.length - 1) {
                arr[i] = arr1[j];
                i++;
                arr[i] = 0;
            } else {
                arr[i] = arr1[j];
            }
        }
    }
}
