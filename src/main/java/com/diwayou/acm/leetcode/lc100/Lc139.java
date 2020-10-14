package com.diwayou.acm.leetcode.lc100;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/word-break/
 *
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定s 是否可以被空格拆分为一个或多个在字典中出现的单词。
 *
 * 说明：
 * 拆分时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 *
 * 示例 1：
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
 *
 * 示例 2：
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
 *     注意你可以重复使用字典中的单词。
 *
 * 示例 3：
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 */
public class Lc139 {

    public static void main(String[] args) {
        System.out.println(new Lc139().wordBreak("aaaaaaa", Arrays.asList("aaaa", "aa")));
    }

    private int[] cache;

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>();
        int minLen = s.length(), maxLen = 1;
        for (String w : wordDict) {
            if (w.length() < minLen) {
                minLen = w.length();
            }
            if (w.length() > maxLen) {
                maxLen = w.length();
            }

            set.add(w);
        }

        cache = new int[s.length() + 1];

        return wordBreakHelper(s, 0, minLen, maxLen, set);
    }

    private boolean wordBreakHelper(String s, int i, int minLen, int maxLen, Set<String> set) {
        if (i == s.length()) {
            return true;
        }

        if (cache[i] != 0) {
            return false;
        }

        if (i + minLen > s.length()) {
            cache[i] = -1;
            return false;
        }

        for (int j = i + minLen; j <= i + maxLen; j++) {
            if (j <= s.length() && set.contains(s.substring(i, j))) {
                if (wordBreakHelper(s, j, minLen, maxLen, set)) {
                    return true;
                }
            }
        }

        cache[i] = -1;
        return false;
    }
}
