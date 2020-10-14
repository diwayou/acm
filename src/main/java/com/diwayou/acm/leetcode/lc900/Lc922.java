package com.diwayou.acm.leetcode.lc900;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/sort-array-by-parity-ii/
 *
 * 给定一个非负整数数组A， A 中一半整数是奇数，一半整数是偶数。
 * 对数组进行排序，以便当A[i] 为奇数时，i也是奇数；当A[i]为偶数时， i 也是偶数。
 * 你可以返回任何满足上述条件的数组作为答案。
 *
 * 示例：
 * 输入：[4,2,5,7]
 * 输出：[4,5,2,7]
 * 解释：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也会被接受。
 * 
 * 提示：
 * 2 <= A.length <= 20000
 * A.length % 2 == 0
 * 0 <= A[i] <= 1000
 */
public class Lc922 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc922().sortArrayByParityII(new int[]{4,2,5,7})));
    }

    public int[] sortArrayByParityII(int[] A) {
        int ei = 0, oi = 1;
        int[] re = new int[A.length];
        for (int a : A) {
            if (a % 2 == 0) {
                re[ei] = a;
                ei += 2;
            } else {
                re[oi] = a;
                oi += 2;
            }
        }

        return re;
    }

    public int[] sortArrayByParityII1(int[] A) {
        int j = 1;
        for (int i = 0; i < A.length; i += 2) {
            if (A[i] % 2 == 1) {
                while (A[j] % 2 == 1) {
                    j += 2;
                }

                int tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
            }
        }

        return A;
    }
}
