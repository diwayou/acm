package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/smallest-range-i/
 * <p>
 * 给定一个整数数组 A，对于每个整数 A[i]，我们可以选择任意x 满足-K <= x <= K，并将x加到A[i]中。
 * 在此过程之后，我们得到一些数组B。
 * 返回 B的最大值和 B的最小值之间可能存在的最小差值。
 * <p>
 * 示例 1：
 * 输入：A = [1], K = 0
 * 输出：0
 * 解释：B = [1]
 * <p>
 * 示例 2：
 * 输入：A = [0,10], K = 2
 * 输出：6
 * 解释：B = [2,8]
 * <p>
 * 示例 3：
 * 输入：A = [1,3,6], K = 3
 * 输出：0
 * 解释：B = [3,3,3] 或 B = [4,4,4]
 * <p>
 * 提示：
 * 1 <= A.length <= 10000
 * 0 <= A[i] <= 10000
 * 0 <= K <= 10000
 */
public class Lc908 {

    public int smallestRangeI(int[] A, int K) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int a : A) {
            if (a < min) {
                min = a;
            }
            if (a > max) {
                max = a;
            }
        }

        if (max - min <= 2 * K) {
            return 0;
        } else {
            return max - min - 2 * K;
        }
    }
}
