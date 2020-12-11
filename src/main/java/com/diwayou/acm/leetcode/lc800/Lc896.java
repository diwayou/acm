package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/monotonic-array/
 * <p>
 * 如果数组是单调递增或单调递减的，那么它是单调的。
 * 如果对于所有 i <= j，A[i] <= A[j]，那么数组 A 是单调递增的。 如果对于所有 i <= j，A[i]> = A[j]，那么数组 A 是单调递减的。
 * 当给定的数组 A是单调数组时返回 true，否则返回 false。
 * <p>
 * 示例 1：
 * 输入：[1,2,2,3]
 * 输出：true
 * <p>
 * 示例 2：
 * 输入：[6,5,4,4]
 * 输出：true
 * <p>
 * 示例 3：
 * 输入：[1,3,2]
 * 输出：false
 * <p>
 * 示例 4：
 * 输入：[1,2,4,5]
 * 输出：true
 * <p>
 * 示例5：
 * 输入：[1,1,1]
 * 输出：true
 * <p>
 * 提示：
 * 1 <= A.length <= 50000
 * -100000 <= A[i] <= 100000
 */
public class Lc896 {

    public boolean isMonotonic(int[] A) {
        if (A.length < 3) {
            return true;
        }

        int len = A.length;
        boolean inc = A[0] < A[len - 1];
        for (int i = 1; i < len; i++) {
            if (inc && A[i] < A[i - 1]) {
                return false;
            } else if (!inc && A[i] > A[i - 1]) {
                return false;
            }
        }

        return true;
    }
}
