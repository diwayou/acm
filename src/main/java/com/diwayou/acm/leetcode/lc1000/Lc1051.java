package com.diwayou.acm.leetcode.lc1000;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/height-checker/
 * <p>
 * 学校在拍年度纪念照时，一般要求学生按照 非递减 的高度顺序排列。
 * 请你返回至少有多少个学生没有站在正确位置数量。该人数指的是：能让所有学生以 非递减 高度排列的必要移动人数。
 * <p>
 * 示例：
 * 输入：[1,1,4,2,1,3]
 * 输出：3
 * 解释：
 * 高度为 4、3 和最后一个 1 的学生，没有站在正确的位置。
 * <p>
 * 提示：
 * 1 <= heights.length <= 100
 * 1 <= heights[i] <= 100
 */
public class Lc1051 {

    public int heightChecker(int[] heights) {
        if (heights.length < 2) {
            return 0;
        }

        int[] c = Arrays.copyOf(heights, heights.length);
        Arrays.sort(c);

        int re = 0;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] != c[i]) {
                re++;
            }
        }

        return re;
    }
}
