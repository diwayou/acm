package com.diwayou.acm.leetcode.lc0;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
 */
public class Lc84 {

    public static void main(String[] args) {
        System.out.println(new Lc84().largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
        System.out.println(new Lc84().largestRectangleArea(new int[]{2, 1, 2, 2, 1}));
    }

    public int largestRectangleArea1(int[] heights) {
        int re = 0;
        for (int i = 0; i < heights.length; i++) {
            int max = heights[i];
            int min = heights[i];
            for (int j = i - 1; j >= 0; j--) {
                if (heights[j] < min) {
                    min = heights[j];
                }

                int area = min * (i - j + 1);
                if (area > max) {
                    max = area;
                }
            }

            if (max > re) {
                re = max;
            }
        }

        return re;
    }

    public int calculateArea(int[] heights, int start, int end) {
        if (start > end)
            return 0;
        int minindex = start;
        for (int i = start; i <= end; i++)
            if (heights[minindex] > heights[i])
                minindex = i;
        return Math.max(heights[minindex] * (end - start + 1), Math.max(calculateArea(heights, start, minindex - 1), calculateArea(heights, minindex + 1, end)));
    }

    public int largestRectangleArea(int[] heights) {
        return calculateArea(heights, 0, heights.length - 1);
    }

    public int largestRectangleArea2(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
            stack.push(i);
        }
        while (stack.peek() != -1)
            maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() - 1));

        return maxarea;
    }
}
