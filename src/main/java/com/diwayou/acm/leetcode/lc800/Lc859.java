package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/buddy-strings/
 *
 * 给定两个由小写字母构成的字符串 A 和 B ，只要我们可以通过交换 A 中的两个字母得到与 B 相等的结果，就返回 true ；否则返回 false。
 */
public class Lc859 {

    public static void main(String[] args) {
        System.out.println(new Lc859().buddyStrings("abcaa", "abcbb"));
    }

    public boolean buddyStrings(String A, String B) {
        if (A.length() != B.length()) {
            return false;
        }

        if (A.equals(B)) {
            boolean duplicate = false;
            int idx;
            boolean[] set = new boolean[26];
            for (int i = 0; i < A.length(); i++) {
                idx = A.charAt(i) - 'a';
                if (set[idx]) {
                    duplicate = true;
                } else {
                    set[idx] = true;
                }
            }

            return duplicate;
        }

        int a = -1;
        int b = -1;
        for (int i = 0; i < A.length(); i++) {
            if (A.charAt(i) == B.charAt(i)) {
                continue;
            } else {
                if (a == -1) {
                    a = i;
                } else if (b == -1) {
                    b = i;
                } else {
                    return false;
                }
            }
        }

        return a != -1 && b != -1 && A.charAt(a) == B.charAt(b) && A.charAt(b) == B.charAt(a);
    }
}
