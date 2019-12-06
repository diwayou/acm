package com.diwayou.acm.leetcode.lc900;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/squares-of-a-sorted-array/
 *
 * 给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
 *
 * 示例 1：
 * 输入：[-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 *
 * 示例 2：
 * 输入：[-7,-3,2,3,11]
 * 输出：[4,9,9,49,121]
 *
 * 提示：
 * 1 <= A.length <= 10000
 * -10000 <= A[i] <= 10000
 * A 已按非递减顺序排序。
 */
public class Lc977 {

    public int[] sortedSquares(int[] A) {
        for (int i = 0; i < A.length; i++) {
            A[i] *= A[i];
        }

        Arrays.sort(A);

        return A;
    }

    public int[] sortedSquares1(int[] A) {
        int[] result = new int[A.length];
        int i = 0;
        int j = A.length - 1;
        int index = A.length - 1;
        while (i <= j) {
            int leftValue = A[i] * A[i];
            int rightValue = A[j] * A[j];
            if (leftValue < rightValue) {
                result[index--] = rightValue;
                j--;
            } else {
                result[index--] = leftValue;
                i++;
            }
        }

        return result;
    }
}
