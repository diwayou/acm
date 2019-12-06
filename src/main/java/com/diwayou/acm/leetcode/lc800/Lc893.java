package com.diwayou.acm.leetcode.lc800;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/groups-of-special-equivalent-strings/
 *
 * 你将得到一个字符串数组 A。
 * 如果经过任意次数的移动，S == T，那么两个字符串 S 和 T 是特殊等价的。
 * 一次移动包括选择两个索引 i 和 j，且 i ％ 2 == j ％ 2，交换 S[j] 和 S [i]。
 * 现在规定，A 中的特殊等价字符串组是 A 的非空子集 S，这样不在 S 中的任何字符串与 S 中的任何字符串都不是特殊等价的。
 * 返回 A 中特殊等价字符串组的数量。
 *
 * 示例 1：
 * 输入：["a","b","c","a","c","c"]
 * 输出：3
 * 解释：3 组 ["a","a"]，["b"]，["c","c","c"]
 *
 * 示例 2：
 * 输入：["aa","bb","ab","ba"]
 * 输出：4
 * 解释：4 组 ["aa"]，["bb"]，["ab"]，["ba"]
 *
 * 示例 3：
 * 输入：["abc","acb","bac","bca","cab","cba"]
 * 输出：3
 * 解释：3 组 ["abc","cba"]，["acb","bca"]，["bac","cab"]
 *
 * 示例 4：
 * 输入：["abcd","cdab","adcb","cbad"]
 * 输出：1
 * 解释：1 组 ["abcd","cdab","adcb","cbad"]
 *  
 * 提示：
 * 1 <= A.length <= 1000
 * 1 <= A[i].length <= 20
 * 所有 A[i] 都具有相同的长度。
 * 所有 A[i] 都只由小写字母组成。
 */
public class Lc893 {

    public static void main(String[] args) {
        System.out.println(new Lc893().numSpecialEquivGroups(new String[]{"abcd","cdab","adcb","cbad"}));
    }

    public int numSpecialEquivGroups(String[] A) {
        if (A.length < 2) {
            return 1;
        }

        int len = A.length;
        int re = 0, j;
        for (int i = 0; i < len; i++) {
            for (j = 0; j < i; j++) {
                if (cmp(A[i], A[j]) == 0) {
                    break;
                }
            }

            if (j == i) {
                re++;
            }
        }

        return re;
    }

    public int cmp(String a, String b) {
        if (a.length() == 1) {
            return a.compareTo(b);
        }

        int[] ao = new int[26];
        int[] ae = new int[26];
        int alen = a.length();
        for (int i = 0; i < alen; i++) {
            if (i % 2 == 0) {
                ae[a.charAt(i) - 'a']++;
            } else {
                ao[a.charAt(i) - 'a']++;
            }
        }

        int[] bo = new int[26];
        int[] be = new int[26];
        int blen = b.length();
        for (int i = 0; i < blen; i++) {
            if (i % 2 == 0) {
                be[b.charAt(i) - 'a']++;
            } else {
                bo[b.charAt(i) - 'a']++;
            }
        }

        if (Arrays.equals(ao, bo) && Arrays.equals(ae, be)) {
            return 0;
        } else {
            return 1;
        }
    }

    public int numSpecialEquivGroups2(String[] A) {
        if (A.length == 1) return 1;
        Set<String> set = new HashSet<>();
        for (String str : A) {
            set.add(sortString(str));
        }

        return set.size();
    }

    private String sortString(String str) {
        int len = str.length();
        char[] oddPosStr = new char[len / 2];
        char[] evenPosStr = new char[len - len / 2];
        int odd = 0, even = 0;
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 0)
                evenPosStr[even++] = str.charAt(i);
            else
                oddPosStr[odd++] = str.charAt(i);
        }

        Arrays.sort(oddPosStr);
        Arrays.sort(evenPosStr);

        StringBuilder sb = new StringBuilder(len);
        sb.append(oddPosStr);
        sb.append(evenPosStr);

        return sb.toString();
    }

    private final static int[] RECORD = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};

    public int numSpecialEquivGroups1(String[] A) {
        HashSet<Integer> res = new HashSet<>();
        for (String s : A) {
            res.add(hash(s));
        }

        return res.size();
    }

    private static int hash(String str) {
        int res = 1;
        //偶数
        for (int i = 0; i < str.length(); i++) {
            int idx = str.charAt(i++) - 'a';
            res *= RECORD[idx];
        }
        res += 5000;
        //奇数
        for (int i = 1; i < str.length(); i++) {
            int idx = str.charAt(i++) - 'a';
            res *= RECORD[idx];
        }

        return res;
    }
}
