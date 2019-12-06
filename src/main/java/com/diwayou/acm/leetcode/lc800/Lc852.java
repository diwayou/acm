package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/peak-index-in-a-mountain-array/
 *
 * 我们把符合下列属性的数组 A 称作山脉：
 * A.length >= 3
 * 存在 0 < i < A.length - 1 使得A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
 * 给定一个确定为山脉的数组，返回任何满足 A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1] 的 i 的值。
 *
 * 示例 1：
 * 输入：[0,1,0]
 * 输出：1
 *
 * 示例 2：
 * 输入：[0,2,1,0]
 * 输出：1
 *  
 * 提示：
 * 3 <= A.length <= 10000
 * 0 <= A[i] <= 10^6
 * A 是如上定义的山脉
 */
public class Lc852 {

    public static void main(String[] args) {
        int[] A = new int[]{45398,72665,105652,156423,211386,315558,603055,770210,827501,925527,995291,991628,981156,979348,928351,923507,916335,916035,911014,868088,858635,848305,836762,836534,803809,722214,599797,589938,545809,507156,505870,505174,476339,463644,424275,412149,404019,403182,397017,302113,282063,238100,209390,197883,186488,70692,65395,50661,44248,5336};

        System.out.println(new Lc852().peakIndexInMountainArray(A));
    }

    public int peakIndexInMountainArray(int[] A) {
        for (int i = 1; i < A.length - 1; i++) {
            if (A[i] > A[i - 1] && A[i] > A[i + 1]) {
                return i;
            }
        }

        return 0;
    }

    public int peakIndexInMountainArray1(int[] A) {
        int bottom = 0;
        int top = A.length - 1;
        while (bottom < top) {
            int mid = bottom + (top - bottom) / 2;
            if (A[mid] > A[mid + 1]) top = mid;
            else bottom = mid + 1;
        }

        return bottom;
    }
}
