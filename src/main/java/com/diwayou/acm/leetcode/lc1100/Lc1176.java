package com.diwayou.acm.leetcode.lc1100;

/**
 * https://leetcode-cn.com/problems/diet-plan-performance/
 */
public class Lc1176 {
    public static void main(String[] args) {
        System.out.println(new Lc1176().dietPlanPerformance(new int[]{6, 13, 8, 7, 10, 1, 12, 11}, 6, 5, 37));
    }

    public int dietPlanPerformance(int[] calories, int k, int lower, int upper) {
        if (calories.length < k) {
            return 0;
        }

        int ans = 0;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += calories[i];
        }
        if (sum < lower) {
            ans--;
        }
        if (sum > upper) {
            ans++;
        }

        for (int i = k; i < calories.length; i++) {
            sum += calories[i] - calories[i - k];
            if (sum < lower) {
                ans--;
            }
            if (sum > upper) {
                ans++;
            }
        }

        return ans;
    }
}
