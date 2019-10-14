package com.diwayou.acm.leetcode.lc900;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/array-of-doubled-pairs/
 * <p>
 * 给定一个长度为偶数的整数数组 A，只有对 A 进行重组后可以满足 “对于每个 0 <= i < len(A) / 2，都有 A[2 * i + 1] = 2 * A[2 * i]” 时，
 * 返回 true；否则，返回 false。
 */
public class Lc954 {

    public static void main(String[] args) {
        System.out.println(new Lc954().canReorderDoubled(new int[]{4, -2, 2, -4}));
    }

    public boolean canReorderDoubled(int[] A) {
        int negCnt = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] < 0) {
                negCnt++;
            }
            A[i] = Math.abs(A[i]);
        }
        if ((negCnt & 1) != 0) {
            return false;
        }

        Arrays.sort(A);

        Map<Integer, Integer> cnt = new HashMap<>();
        Integer v;
        for (int a : A) {
            v = cnt.get(a);
            if (v == null) {
                cnt.put(a, 1);
            } else {
                cnt.put(a, v + 1);
            }
        }

        for (int i = 0; i < A.length; i++) {
            v = cnt.get(A[i]);

            if (v == null) {
                return false;
            } else {
                if (v == Integer.MAX_VALUE) {
                    continue;
                }

                cnt.put(A[i], v - 1 == 0 ? Integer.MAX_VALUE : v - 1);

                v = cnt.get((A[i] * 2));
                if (v == null || v == Integer.MAX_VALUE) {
                    return false;
                } else {
                    cnt.put(A[i] * 2, v - 1 == 0 ? Integer.MAX_VALUE : v - 1);
                }
            }
        }

        return true;
    }

    public boolean canReorderDoubled1(int[] A) {
        // 记录负数
        short[] negative = new short[100000];
        // 记录正数
        short[] positive = new short[100000];

        // 负数必须是2的整数倍，最小的正数必须是2的倍数
        int maxPositive = 0, minNegative = 0, actualNegativeCount = 0;
        for (int i : A) {
            if (i > 0) {
                positive[i]++;
                maxPositive = Math.max(maxPositive, i);
            } else {
                i = -i;
                negative[i]++;
                actualNegativeCount++;
                minNegative = Math.max(minNegative, i);
            }
        }

        if ((actualNegativeCount & 1) != 0
                || (positive[0] & 1) != 0) return false;

        return search(positive, maxPositive) && search(negative, minNegative);
    }

    private boolean search(short[] arr, int max) {
        int mid = max >> 1;
        for (int i = 1; i <= mid; i++) {
            if (arr[i] > 0) {
                arr[i * 2] -= arr[i];
            } else if (arr[i] < 0) return false;
        }

        for (int i = mid + 1; i <= max; i++) {
            if (arr[i] != 0) return false;
        }
        return true;
    }
}
