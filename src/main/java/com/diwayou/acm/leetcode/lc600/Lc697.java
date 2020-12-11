package com.diwayou.acm.leetcode.lc600;

/**
 * https://leetcode-cn.com/problems/degree-of-an-array/
 * <p>
 * 给定一个非空且只包含非负数的整数数组nums, 数组的度的定义是指数组里任一元素出现频数的最大值。
 * 你的任务是找到与nums拥有相同大小的度的最短连续子数组，返回其长度。
 * <p>
 * 示例 1:
 * 输入: [1, 2, 2, 3, 1]
 * 输出: 2
 * 解释:
 * 输入数组的度是2，因为元素1和2的出现频数最大，均为2.
 * 连续子数组里面拥有相同度的有如下所示:
 * [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
 * 最短连续子数组[2, 2]的长度为2，所以返回2.
 * <p>
 * 示例 2:
 * 输入: [1,2,2,3,1,4,2]
 * 输出: 6
 * <p>
 * 注意:
 * nums.length在1到50,000区间范围内。
 * nums[i]是一个在0到49,999范围内的整数。
 */
public class Lc697 {

    public static void main(String[] args) {
        System.out.println(new Lc697().findShortestSubArray(new int[]{1, 2, 2, 3, 1, 4, 2}));
    }

    public int findShortestSubArray(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }

        int min = 49999, max = 0;
        for (int n : nums) {
            if (n < min) {
                min = n;
            }
            if (n > max) {
                max = n;
            }
        }

        int len = max - min + 1;
        int[] cnts = new int[len];
        int maxCnt = 0, t;
        for (int n : nums) {
            t = n - min;
            cnts[t]++;
            if (cnts[t] > maxCnt) {
                maxCnt = cnts[t];
            }
        }

        int re = 500000;
        for (int i = 0; i < cnts.length; i++) {
            // 左右index也可以缓存，这样就不需要遍历查找了，这个是时间和空间的取舍
            if (cnts[i] == maxCnt) {
                int a = 0, b = nums.length - 1;
                t = i + min;
                while (nums[a] != t) {
                    a++;
                }
                while (nums[b] != t) {
                    b--;
                }

                t = b - a + 1;
                if (t < re) {
                    re = t;
                }
            }
        }

        return re;
    }
}
