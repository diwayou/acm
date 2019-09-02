package com.diwayou.acm.leetcode.sort;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/height-checker/submissions/
 */
public class HeightChecker {
    public static void main(String[] args) {
        int[] arr = {1,1,4,2,1,3};
        System.out.println(new HeightChecker().heightChecker(arr));
    }

    public int heightChecker(int[] heights) {
        int[] cHeights = Arrays.copyOf(heights, heights.length);
        Arrays.sort(cHeights);

        int re = 0;
        for (int i = 0; i < heights.length; ++i) {
            if (heights[i] != cHeights[i]) {
                ++re;
            }
        }

        return re;
    }

    public int heightChecker1(int[] heights) {
        int[] histogram = new int[101];
        for (int i : heights) {
            ++histogram[i];
        }

        int re = 0;
        for (int i = 1, j = 0; i < histogram.length; ++i) {
            while (histogram[i]-- > 0) {
                if (heights[j++] != i) {
                    ++re;
                }
            }
        }

        return re;
    }
}
