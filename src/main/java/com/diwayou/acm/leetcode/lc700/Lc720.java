package com.diwayou.acm.leetcode.lc700;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/longest-word-in-dictionary/
 * <p>
 * 给出一个字符串数组words组成的一本英语词典。从中找出最长的一个单词，该单词是由words词典中其他单词逐步添加一个字母组成。
 * 若其中有多个可行的答案，则返回答案中字典序最小的单词。
 * 若无答案，则返回空字符串。
 */
public class Lc720 {

    public static void main(String[] args) {
        System.out.println(new Lc720().longestWord(new String[]{"yo", "ew", "fc", "zrc", "yodn", "fcm", "qm", "qmo", "fcmz", "z", "ewq", "yod", "ewqz", "y"}));
    }

    public String longestWord(String[] words) {
        //对数组排序，再利用Set对字母存储，小的单词一定包含在后面大的单词里面。后面只需要取前缀相同的
        //对字母排序后，第一个单词一定是共有的，后面只需在此基础上添加
        Arrays.sort(words);

        Set<String> set = new HashSet<>();
        String res = "";
        for (String s : words) {
            //如果单词只有一个字母，那一定是共有的
            if (s.length() == 1 || set.contains(s.substring(0, s.length() - 1))) {
                res = s.length() > res.length() ? s : res;
                set.add(s);
            }
        }

        return res;
    }
}
