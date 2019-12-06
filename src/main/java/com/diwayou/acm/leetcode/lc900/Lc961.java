package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/n-repeated-element-in-size-2n-array/
 *
 * 在大小为 2N 的数组 A 中有 N+1 个不同的元素，其中有一个元素重复了 N 次。
 * 返回重复了 N 次的那个元素。
 *
 * 示例 1：
 * 输入：[1,2,3,3]
 * 输出：3
 *
 * 示例 2：
 * 输入：[2,1,2,5,3,2]
 * 输出：2
 *
 * 示例 3：
 * 输入：[5,1,5,2,5,3,5,4]
 * 输出：5
 *
 * 提示：
 * 4 <= A.length <= 10000
 * 0 <= A[i] < 10000
 * A.length 为偶数
 */
public class Lc961 {

    public static void main(String[] args) {
        System.out.println(new Lc961().repeatedNTimes(new int[]{5,1,4,2,5,3,5,5}));
    }

    public int repeatedNTimes(int[] A) {
        int a = A[0], b = A[1], c = A[2];
        if (a == b || a == c) {
            return a;
        } else if (b == c) {
            return b;
        }

        for (int i = 3; i < A.length; i++) {
            if (A[i] == a || A[i] == b || A[i] == c) {
                return A[i];
            } else {
                a = A[i];
            }
        }

        return a;
    }

    public int repeatedNTimes1(int[] A) {
        for (int k = 1; k <= 3; ++k)
            for (int i = 0; i < A.length - k; ++i)
                if (A[i] == A[i + k])
                    return A[i];

        throw null;
    }
}
