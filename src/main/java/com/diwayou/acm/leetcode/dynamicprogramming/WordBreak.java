package com.diwayou.acm.leetcode.dynamicprogramming;

// Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be
// segmented into a space-separated sequence of one or more dictionary words. You may assume the dictionary
// does not contain duplicate words.

// For example, given
// s = "leetcode",
// dict = ["leet", "code"].

// Return true because "leetcode" can be segmented as "leet code".

import com.google.common.collect.Sets;

import java.util.Set;

public class WordBreak {

    public static boolean wordBreak(String s, Set<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];

        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    public static void main(String[] args) {
        String s = "leetcode";
        Set<String> wordDict = Sets.newHashSet("leet", "code");

        System.out.println(wordBreak(s, wordDict));

        s = "aababcabcd";
        wordDict = Sets.newHashSet("a", "ab", "abc", "abcd");
        System.out.println(wordBreak(s, wordDict));
    }
}
