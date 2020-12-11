package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/minimum-swaps-to-make-sequences-increasing/
 * <p>
 * 我们有两个长度相等且不为空的整型数组A和B。
 * 我们可以交换A[i]和B[i]的元素。注意这两个元素在各自的序列中应该处于相同的位置。
 * 在交换过一些元素之后，数组A和B都应该是严格递增的（数组严格递增的条件仅为A[0] < A[1] < A[2] < ... < A[A.length - 1]）。
 * 给定数组A和B，请返回使得两个数组均保持严格递增状态的最小交换次数。假设给定的输入总是有效的。
 * <p>
 * 示例:
 * 输入: A = [1,3,5,4], B = [1,2,3,7]
 * 输出: 1
 * 解释:
 * 交换 A[3] 和 B[3] 后，两个数组如下:
 * A = [1, 3, 5, 7] ， B = [1, 2, 3, 4]
 * 两个数组均为严格递增的。
 * <p>
 * 注意:
 * A, B两个数组的长度总是相等的，且长度的范围为[1, 1000]。
 * A[i], B[i]均为[0, 2000]区间内的整数。
 */
public class Lc801 {

    public static void main(String[] args) {
        System.out.println(new Lc801().minSwap(new int[]{3, 3, 8, 9, 10}, new int[]{1, 7, 4, 6, 8}));
    }

    /*
        我们在判断 A[i] 和 B[i] 是否交换时，只需要考虑它们之前的一个元素 A[i - 1] 和 B[i - 1] 是否被交换就可以了。这是因为要使得序列严格递增，
        每个元素只要大于它之前的那个元素就行。这样以来，我们就可以用动态规划的方法来解决这个问题。
        我们用 n1 表示数组 A 和 B 满足前 i - 1 个元素分别严格递增，并且 A[i - 1] 和 B[i - 1] 未被交换的最小交换次数，用 s1 表示 A[i - 1] 和
        B[i - 1] 被交换的最小交换次数。当我们知道了 n1 和 s1 的值之后，我们需要通过转移得到 n2 和 s2（和之前的定义相同，只不过考虑的是 A[i] 和 B[i] 这两个元素）的值。
        我们令 a1 = A[i - 1], b1 = B[i - 1] 以及 a2 = A[i], b2 = B[i]。如果 a1 < a2 并且 b1 < b2，那如果 a1 和 b1 未被交换，a2 和 b2
        就可以不交换；同样如果 a1 和 b1 被交换了，a2 和 b2 在交换后满足严格递增的条件。因此我们得到了两个状态转移方程：n2 = min(n2, n1) 以及
        s2 = min(s2, s1 + 1)，分别表示 a1 和 b1 未被交换和被交换的情况。
        如果 a1 < b2 并且 b1 < a2，那么要满足严格递增的条件，必须要交换其中的一列，要么是 A[i - 1] 和 B[i - 1]，要么是 A[i] 和 B[i]。
        因此有这两个状态转移方程：n2 = min(n2, s1) 以及 s2 = min(s2, n1 + 1)。
        上面两种情况可能会同时发生（并不是互斥的），因此每一次状态转移的时候，这两种情况都必须要考虑。最后的答案即为 n2 和 s2 中的最小值。
     */
    public int minSwap(int[] A, int[] B) {
        // n: natural, s: swapped
        int n1 = 0, s1 = 1;
        for (int i = 1; i < A.length; ++i) {
            int n2 = Integer.MAX_VALUE, s2 = Integer.MAX_VALUE;
            if (A[i - 1] < A[i] && B[i - 1] < B[i]) {
                n2 = Math.min(n2, n1);
                s2 = Math.min(s2, s1 + 1);
            }
            if (A[i - 1] < B[i] && B[i - 1] < A[i]) {
                n2 = Math.min(n2, s1);
                s2 = Math.min(s2, n1 + 1);
            }
            n1 = n2;
            s1 = s2;
        }

        return Math.min(n1, s1);
    }
}
