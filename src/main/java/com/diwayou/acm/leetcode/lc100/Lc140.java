package com.diwayou.acm.leetcode.lc100;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/word-break-ii/
 * <p>
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，在字符串中增加空格来构建一个句子，使得句子中所有的单词都在词典中。返回所有这些可能的句子。
 * 说明：
 * 分隔时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 */
public class Lc140 {

    public static void main(String[] args) {
        new Lc140().wordBreak("catsanddog",
                Arrays.asList("cats", "dog", "sand", "and", "cat"))
                .forEach(System.out::println);
//        new Lc140().wordBreak1("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
//                Arrays.asList("a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"))
//                .forEach(System.out::println);
    }

    public List<String> wordBreak1(String s, List<String> wordDict) {
        Set<String> wd = new HashSet<>();
        wd.addAll(wordDict);
        return wordBreakHelper(s, wd, s.length());
    }

    private HashMap<Integer, List<String>> map = new HashMap<>();

    public List<String> wordBreakHelper(String s, Set<String> wordDict, int i) {
        if (map.containsKey(i)) {
            return map.get(i);
        }

        LinkedList<String> res = new LinkedList<>();
        if (i <= 0) {
            return Collections.singletonList("");
        }

        String sub;
        for (int j = i - 1; j >= 0; j--) {
            sub = s.substring(j, i);
            if (wordDict.contains(sub)) {
                List<String> list = wordBreakHelper(s, wordDict, j);
                for (String l : list) {
                    res.add(l + (l.isEmpty() ? "" : " ") + sub);
                }
            }
        }

        map.put(i, res);

        return res;
    }

    // 超时。。。
    public List<String> wordBreak2(String s, List<String> wordDict) {
        Set<String> wd = new HashSet<>();
        wd.addAll(wordDict);

        List<String>[] dp = new List[s.length() + 1];
        dp[0] = Collections.singletonList("");

        String sub;
        List<String> tmp;
        for (int i = 1; i <= s.length(); i++) {
            tmp = new LinkedList<>();
            for (int j = i - 1; j >= 0; j--) {
                sub = s.substring(j, i);
                if (wordDict.contains(sub)) {
                    for (String l : dp[j]) {
                        tmp.add(l + (l.isEmpty() ? "" : " ") + sub);
                    }
                }
            }

            dp[i] = tmp;
        }

        return dp[s.length()];
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<String>(wordDict);
        List<String> res = new ArrayList<String>();
        int length = s.length();
        boolean[] dp = new boolean[length + 1];
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int len = 0;

        for (String tmp : wordDict) {
            len = tmp.length();
            if (len > max) {
                max = len;
            }
            if (len < min) {
                min = len;
            }
        }

        dp[0] = true;
        for (int i = min; i <= length; i++) {
            for (int j = i - min; j >= i - max && j >= 0; j--) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        if (!dp[length]) {
            return res;
        }

        StringBuilder sb = new StringBuilder();
        dfs(s, set, sb, res, 0);
        return res;

    }

    public void dfs(String s, Set<String> wordDict, StringBuilder sb, List<String> res, int start) {
        if (start == s.length()) {
            res.add(sb.toString().trim());
            return;
        }

        for (int i = start; i < s.length(); i++) {
            String str = s.substring(start, i + 1);
            if (wordDict.contains(str)) {
                int length = sb.length();
                sb.append(str).append(" ");
                dfs(s, wordDict, sb, res, i + 1);
                sb.setLength(length);
            }
        }
    }
}
