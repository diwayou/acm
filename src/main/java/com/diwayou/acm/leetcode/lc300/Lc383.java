package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/ransom-note/
 * <p>
 * 给定一个赎金信 (ransom) 字符串和一个杂志(magazine)字符串，判断第一个字符串ransom能不能由第二个字符串magazines里面的字符构成。如果可以构成，返回 true ；否则返回 false。
 * (题目说明：为了不暴露赎金信字迹，要从杂志上搜索各个需要的字母，组成单词来表达意思。)
 * <p>
 * 注意：
 * 你可以假设两个字符串均只含有小写字母。
 * <p>
 * canConstruct("a", "b") -> false
 * canConstruct("aa", "ab") -> false
 * canConstruct("aa", "aab") -> true
 */
public class Lc383 {

    public boolean canConstruct(String ransomNote, String magazine) {
        int[] cnt = new int[26];
        for (char c : magazine.toCharArray()) {
            cnt[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            if (--cnt[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

    public boolean canConstruct1(String ransomNote, String magazine) {
        int[] map = new int[26];
        for (char c : ransomNote.toCharArray()) {
            int idx = magazine.indexOf(c, map[c - 'a']);
            if (idx == -1) {
                return false;
            }

            map[c - 'a'] = idx + 1;
        }

        return true;
    }
}
