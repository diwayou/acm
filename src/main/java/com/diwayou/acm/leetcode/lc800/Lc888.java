package com.diwayou.acm.leetcode.lc800;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/fair-candy-swap/
 *
 * 爱丽丝和鲍勃有不同大小的糖果棒：A[i] 是爱丽丝拥有的第 i块糖的大小，B[j] 是鲍勃拥有的第 j块糖的大小。
 * 因为他们是朋友，所以他们想交换一个糖果棒，这样交换后，他们都有相同的糖果总量。（一个人拥有的糖果总量是他们拥有的糖果棒大小的总和。）
 * 返回一个整数数组 ans，其中 ans[0] 是爱丽丝必须交换的糖果棒的大小，ans[1]是 Bob 必须交换的糖果棒的大小。
 * 如果有多个答案，你可以返回其中任何一个。保证答案存在。
 *
 * 示例 1：
 * 输入：A = [1,1], B = [2,2]
 * 输出：[1,2]
 *
 * 示例 2：
 * 输入：A = [1,2], B = [2,3]
 * 输出：[1,2]
 *
 * 示例 3：
 * 输入：A = [2], B = [1,3]
 * 输出：[2,3]
 *
 * 示例 4：
 * 输入：A = [1,2,5], B = [2,4]
 * 输出：[5,4]
 *
 * 提示：
 * 1 <= A.length <= 10000
 * 1 <= B.length <= 10000
 * 1 <= A[i] <= 100000
 * 1 <= B[i] <= 100000
 * 保证爱丽丝与鲍勃的糖果总量不同。
 * 答案肯定存在。
 */
public class Lc888 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc888().fairCandySwap(new int[]{1,2,5}, new int[]{2,4})));
    }

    public int[] fairCandySwap(int[] A, int[] B) {
        int sumA = 0, sumB = 0;
        for (int a : A) {
            sumA += a;
        }
        for (int b : B) {
            sumB += b;
        }

        Arrays.sort(A);
        Arrays.sort(B);

        if (sumA > sumB) {
            return find(A, B, (sumA - sumB) / 2, true);
        } else {
            return find(B, A, (sumB - sumA) / 2, false);
        }
    }

    private int[] find(int[] A, int[] B, int target, boolean normal) {
        int i = 0, j = 0;
        while (i < A.length && j < B.length) {
            if (A[i] - B[j] > target) {
                j++;
            } else if (A[i] - B[j] < target) {
                i++;
            } else {
                if (normal) {
                    return new int[]{A[i], B[j]};
                } else {
                    return new int[]{B[j], A[i]};
                }
            }
        }

        return new int[]{};
    }

    public int[] fairCandySwap1(int[] A, int[] B) {
        Arrays.sort(B);
        int sum_a = 0;
        int sum_b = 0;
        int sum;
        for (int x : A) {
            sum_a += x;
        }
        for (int x : B) {
            sum_b += x;
        }
        sum = sum_a + sum_b;
        int ban = sum / 2;
        int cha = ban - sum_a;
        for (int i1 : A) {
            int h = i1 + cha;
            if (Arrays.binarySearch(B, h) >= 0) {
                return new int[]{i1, h};
            }
        }
        return new int[]{0, 0};
    }

    public int[] fairCandySwap2(int[] A, int[] B) {
        int sa = 0, sb = 0;  // sum of A, B respectively
        for (int x : A) sa += x;
        for (int x : B) sb += x;
        int delta = (sb - sa) / 2;
        // If Alice gives x, she expects to receive x + delta

        Set<Integer> setB = new HashSet();
        for (int x : B) setB.add(x);

        for (int x : A)
            if (setB.contains(x + delta))
                return new int[]{x, x + delta};

        throw null;
    }
}
