package com.diwayou.acm.leetcode.lc1100;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/distribute-candies-to-people/
 */
public class Lc1103 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc1103().distributeCandies(7, 4)));
    }

    public int[] distributeCandies(int candies, int num_people) {
        int[] result = new int[num_people];

        int i = 1;
        int cur = 0;
        int idx = 0;
        while ((cur + i) <= candies) {
            result[idx++] += i;

            if (idx >= num_people) {
                idx = 0;
            }
            cur += i++;
        }

        result[idx] += candies - cur;

        return result;
    }

    public int[] distributeCandies1(int candies, int num_people) {
        int[] res = new int[num_people];
        int m = (int) Math.sqrt(candies * 2);
        if ((long) m * (m + 1) / 2 > candies) {
            --m;
        }
        int left = candies - m * (m + 1) / 2;
        for (int i = 0; i < num_people; ++i) {
            int t = m / num_people + (i < m % num_people ? 1 : 0);
            res[i] = t * (i + 1) + t * (t - 1) / 2 * num_people;
        }
        res[m % num_people] += left;

        return res;
    }
}
