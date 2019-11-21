package com.diwayou.acm.leetcode.lc500;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/relative-ranks/
 *
 * 给出 N 名运动员的成绩，找出他们的相对名次并授予前三名对应的奖牌。前三名运动员将会被分别授予 “金牌”，“银牌” 和“ 铜牌”（"Gold Medal", "Silver Medal", "Bronze Medal"）。
 * (注：分数越高的选手，排名越靠前。)
 *
 * 示例 1:
 * 输入: [5, 4, 3, 2, 1]
 * 输出: ["Gold Medal", "Silver Medal", "Bronze Medal", "4", "5"]
 * 解释: 前三名运动员的成绩为前三高的，因此将会分别被授予 “金牌”，“银牌”和“铜牌” ("Gold Medal", "Silver Medal" and "Bronze Medal").
 * 余下的两名运动员，我们只需要通过他们的成绩计算将其相对名次即可。
 *
 * 提示:
 * N 是一个正整数并且不会超过 10000。
 * 所有运动员的成绩都不相同。
 */
public class Lc506 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc506().findRelativeRanks(new int[]{1, 4, 3, 2, 5})));
    }

    public String[] findRelativeRanks(int[] nums) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int n : nums) {
            if (n < min) {
                min = n;
            }
            if (n > max) {
                max = n;
            }
        }

        int[] s = new int[max - min + 1];

        for (int i = 0; i < nums.length; i++) {
            s[nums[i] - min] = i + 1;
        }

        String[] re = new String[nums.length];
        String[] m = new String[]{"Gold Medal", "Silver Medal", "Bronze Medal"};
        int idx = 0;
        for (int i = s.length - 1; i >= 0; i--) {
            if (s[i] != 0) {
                if (idx < 3) {
                    re[s[i] - 1] = m[idx++];
                } else {
                    re[s[i] - 1] = String.valueOf(++idx);
                }
            }
        }

        return re;
    }
}
