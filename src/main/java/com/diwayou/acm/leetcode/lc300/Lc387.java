package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/first-unique-character-in-a-string/
 * <p>
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 * <p>
 * 案例:
 * s = "leetcode"
 * 返回 0.
 * <p>
 * s = "loveleetcode",
 * 返回 2.
 * <p>
 * 注意事项：您可以假定该字符串只包含小写字母。
 */
public class Lc387 {

    public static void main(String[] args) {
        System.out.println(new Lc387().firstUniqChar1("abcabcz"));
    }

    public int firstUniqChar(String s) {
        int[] cnt = new int[26];
        char[] sa = s.toCharArray();
        for (int i = 0; i < sa.length; i++) {
            if (cnt[sa[i] - 'a'] == 0) {
                cnt[sa[i] - 'a'] = i + 1;
            } else {
                cnt[sa[i] - 'a'] = Integer.MAX_VALUE;
            }
        }

        int re = Integer.MAX_VALUE;
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] != 0 && cnt[i] < re) {
                re = cnt[i];
            }
        }

        return re == Integer.MAX_VALUE ? -1 : re - 1;
    }

    public int firstUniqChar1(String s) {
        if (s == null) {
            return -1;
        }
        int min = s.length();
        for (char l = 'a'; l <= 'z'; l++) {
            int first = s.indexOf(l);
            if (first == -1) {
                continue;
            }
            if (first == s.lastIndexOf(l) && min > first) {
                min = first;
            }
        }

        return min == s.length() ? -1 : min;
    }
}
