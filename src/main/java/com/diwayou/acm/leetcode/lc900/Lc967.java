package com.diwayou.acm.leetcode.lc900;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/numbers-with-same-consecutive-differences/
 * <p>
 * 返回所有长度为 N 且满足其每两个连续位上的数字之间的差的绝对值为 K的非负整数。
 * 请注意，除了数字 0 本身之外，答案中的每个数字都不能有前导零。例如，01因为有一个前导零，所以是无效的；但 0是有效的。
 * 你可以按任何顺序返回答案。
 * <p>
 * 1 <= N <= 9
 * 0 <= K <= 9
 * <p>
 * 输入：N = 3, K = 7
 * 输出：[181,292,707,818,929]
 * 解释：注意，070 不是一个有效的数字，因为它有前导零。
 * <p>
 * 输入：N = 2, K = 1
 * 输出：[10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]
 */
public class Lc967 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc967().numsSameConsecDiff(2, 0)));
    }

    public int[] numsSameConsecDiff(int N, int K) {
        if (N == 1) {
            return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        }

        List<Integer> re = new ArrayList<>();

        List<Integer> backtrack = new ArrayList<>(N);
        for (int i = 1; i <= 9; i++) {
            if (i + K <= 9 || i - K >= 0) {
                backtrack.add(i);
                numsSameConsecDiffHelper(N, K, backtrack, re);
                backtrack.remove(backtrack.size() - 1);
            }
        }

        int[] result = new int[re.size()];
        for (int i = 0; i < re.size(); i++) {
            result[i] = re.get(i);
        }

        return result;
    }

    private void numsSameConsecDiffHelper(int n, int k, List<Integer> backtrack, List<Integer> re) {
        if (backtrack.size() == n) {
            int t = 0;
            for (int i : backtrack) {
                t = t * 10 + i;
            }
            re.add(t);
            return;
        }

        int last = backtrack.get(backtrack.size() - 1);
        int next;
        next = last - k;
        if (next >= 0) {
            backtrack.add(next);
            numsSameConsecDiffHelper(n, k, backtrack, re);
            backtrack.remove(backtrack.size() - 1);
        }

        next = last + k;
        if (k != 0 && next <= 9) {
            backtrack.add(next);
            numsSameConsecDiffHelper(n, k, backtrack, re);
            backtrack.remove(backtrack.size() - 1);
        }
    }

    public int[] numsSameConsecDiff1(int N, int K) {
        if (N == 1) {
            return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            help(res, i, N - 1, K);
        }
        int[] rrr = new int[res.size()];
        for (int j = 0; j < rrr.length; j++) {
            rrr[j] = res.get(j);
        }
        return rrr;
    }

    void help(List<Integer> r, int cur, int left, int K) {
        if (left == 0) {
            r.add(cur);
            return;
        }
        int last = cur % 10;
        if (last + K <= 9) {
            help(r, cur * 10 + last + K, left - 1, K);
        }
        if (last - K >= 0 && K != 0) {
            help(r, cur * 10 + last - K, left - 1, K);
        }

    }
}
