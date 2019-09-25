package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/valid-mountain-array/
 */
public class Lc941 {

    public boolean validMountainArray(int[] A) {
        if (A.length < 3) {
            return false;
        }

        if (A[0] >= A[1]) {
            return false;
        }

        boolean inc = true;
        for (int i = 2; i < A.length; i++) {
            if (A[i] == A[i - 1]) {
                return false;
            }

            if (inc && A[i] < A[i - 1]) {
                inc = false;
                continue;
            }

            if (!inc && A[i] > A[i - 1]) {
                return false;
            }
        }

        return !inc;
    }

    public boolean validMountainArray1(int[] A) {
        if (A.length < 3) {
            return false;
        }

        int left = 0;
        int right = A.length - 1;
        while (left < A.length - 2 && A[left] < A[left + 1]) {
            left++;
        }
        while (right > 1 && A[right] < A[right - 1]) {
            right--;
        }

        return left == right;
    }
}
