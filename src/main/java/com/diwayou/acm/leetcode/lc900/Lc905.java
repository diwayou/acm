package com.diwayou.acm.leetcode.lc900;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/sort-array-by-parity/
 *
 * 给定一个非负整数数组 A，返回一个数组，在该数组中，A 的所有偶数元素之后跟着所有奇数元素。
 * 你可以返回满足此条件的任何数组作为答案。
 *
 * 示例：
 * 输入：[3,1,2,4]
 * 输出：[2,4,3,1]
 * 输出 [4,2,3,1]，[2,4,1,3] 和 [4,2,1,3] 也会被接受。
 * 
 * 提示：
 * 1 <= A.length <= 5000
 * 0 <= A[i] <= 5000
 */
public class Lc905 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc905().sortArrayByParity(new int[]{3,1,2,4})));
    }

    public int[] sortArrayByParity(int[] A) {
        if (A.length < 2) {
            return A;
        }

        int t, i = 0, j = A.length - 1;
        while (i < j) {
            while (i < j && A[i] % 2 == 0) {
                i++;
            }
            while (i < j && A[j] % 2 != 0) {
                j--;
            }

            if (i < j) {
                t = A[i];
                A[i] = A[j];
                A[j] = t;
            }
        }

        return A;
    }
}
