package com.diwayou.acm.leetcode.lc900;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/di-string-match/
 *
 * 给定只含 "I"（增大）或 "D"（减小）的字符串 S ，令 N = S.length。
 * 返回 [0, 1, ..., N] 的任意排列 A 使得对于所有 i = 0, ..., N-1，都有：
 * 如果 S[i] == "I"，那么 A[i] < A[i+1]
 * 如果 S[i] == "D"，那么 A[i] > A[i+1]
 *
 * 示例 1：
 * 输入："IDID"
 * 输出：[0,4,1,3,2]
 *
 * 示例 2：
 * 输入："III"
 * 输出：[0,1,2,3]
 *
 * 示例 3：
 * 输入："DDI"
 * 输出：[3,2,0,1]
 *
 * 提示：
 * 1 <= S.length <= 1000
 * S 只包含字符 "I" 或 "D"。
 */
public class Lc942 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc942().diStringMatch("DDI")));
    }

    public int[] diStringMatch(String S) {
        int i = 0, j = S.length(), idx = 0;
        int[] re = new int[S.length() + 1];
        for (char c : S.toCharArray()) {
            if (c == 'I') {
                re[idx++] = i++;
            } else {
                re[idx++] = j--;
            }
        }
        re[idx] = i;

        return re;
    }
}
