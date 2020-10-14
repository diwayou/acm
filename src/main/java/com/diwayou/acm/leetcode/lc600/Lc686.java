package com.diwayou.acm.leetcode.lc600;

/**
 * https://leetcode-cn.com/problems/repeated-string-match/
 *
 * 给定两个字符串 A 和 B, 寻找重复叠加字符串A的最小次数，使得字符串B成为叠加后的字符串A的子串，如果不存在则返回 -1。
 * 举个例子，A = "abcd"，B = "cdabcdab"。
 * 答案为 3，因为 A 重复叠加三遍后为“abcdabcdabcd”，此时 B 是其子串；A 重复叠加两遍后为"abcdabcd"，B 并不是其子串。
 *
 * 注意:
 * A与B字符串的长度在1和10000区间范围内。
 */
public class Lc686 {

    public static void main(String[] args) {
        System.out.println(new Lc686().repeatedStringMatch("abc","cabcabca"));
    }

    public int repeatedStringMatch(String A, String B) {
        int times = (B.length() - 1) / A.length() + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(A);
        }
        if (sb.lastIndexOf(B) > -1) {
            return times;
        }
        sb.append(A);
        if (sb.lastIndexOf(B) > -1) {
            return times + 1;
        }

        return -1;
    }

    public int repeatedStringMatch2(String A, String B) {
        int i = 1;
        StringBuilder s = new StringBuilder(A);
        int blength = B.length();
        while (s.length() < blength) {
            s.append(A);
            i++;
        }

        return s.lastIndexOf(B) == -1 ? ((s.append(A)).lastIndexOf(B) == -1 ? -1 : i + 1) : i;
    }
}
