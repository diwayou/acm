package com.diwayou.acm.leetcode.lc800;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/shortest-distance-to-a-character/
 *
 * 给定一个字符串 S 和一个字符 C。返回一个代表字符串 S 中每个字符到字符串 S 中的字符 C 的最短距离的数组。
 *
 * 示例 1:
 * 输入: S = "loveleetcode", C = 'e'
 * 输出: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
 *
 * 说明:
 * 字符串 S 的长度范围为 [1, 10000]。
 * C 是一个单字符，且保证是字符串 S 里的字符。
 * S 和 C 中的所有字母均为小写字母。
 */
public class Lc821 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc821().shortestToChar("loveleetcode", 'e')));
    }

    public int[] shortestToChar1(String S, char C) {
        int[] re = new int[S.length()];
        int d = 10000;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == C) {
                re[i] = 0;
                d = 1;
                for (int j = i - 1; j >= 0; j--) {
                    if (re[j] <= d) {
                        break;
                    }

                    re[j] = d++;
                }
                d = 1;
            } else {
                re[i] = d++;
            }
        }

        return re;
    }

    public int[] shortestToChar(String S, char C) {
        int N = S.length();
        int[] ans = new int[N];
        int prev = Integer.MIN_VALUE / 2;

        for (int i = 0; i < N; ++i) {
            if (S.charAt(i) == C) {
                prev = i;
            }

            ans[i] = i - prev;
        }

        prev = Integer.MAX_VALUE / 2;
        for (int i = N - 1; i >= 0; --i) {
            if (S.charAt(i) == C) {
                prev = i;
            }

            ans[i] = Math.min(ans[i], prev - i);
        }

        return ans;
    }
}
