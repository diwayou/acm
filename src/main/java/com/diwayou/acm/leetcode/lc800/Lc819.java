package com.diwayou.acm.leetcode.lc800;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/most-common-word/
 * <p>
 * 给定一个段落 (paragraph) 和一个禁用单词列表 (banned)。返回出现次数最多，同时不在禁用列表中的单词。题目保证至少有一个词不在禁用列表中，而且答案唯一。
 * <p>
 * 禁用列表中的单词用小写字母表示，不含标点符号。段落中的单词不区分大小写。答案都是小写字母。
 */
public class Lc819 {
    public static void main(String[] args) {
        String paragraph = "Bob. hIt, baLl";
        String[] banned = new String[]{"bob", "hit"};

        System.out.println(new Lc819().mostCommonWord(paragraph, banned));
    }

    public String mostCommonWord1(String paragraph, String[] banned) {
        Set<String> bannedSet = new HashSet<>();
        for (String b : banned) {
            bannedSet.add(b);
        }

        Map<String, Integer> cntMap = new HashMap<>();
        String[] words = paragraph.split("\\W+");
        String maxWord = null;
        int max = 0;
        for (String w : words) {
            w = w.toLowerCase();
            if (bannedSet.contains(w)) {
                continue;
            }

            Integer cnt = cntMap.get(w);
            if (cnt == null) {
                cnt = 1;
            } else {
                cnt = cnt + 1;
            }
            cntMap.put(w, cnt);

            if (cnt > max) {
                max = cnt;
                maxWord = w;
            }
        }

        return maxWord;
    }

    private static class Node {
        private int value;
        private Node[] next = new Node[26];
    }

    public String mostCommonWord(String paragraph, String[] banned) {
        Node root = new Node();

        char[] chars;
        for (String b : banned) {
            chars = b.toCharArray();
            incrAndGet(root, chars, 0, chars.length, true);
        }

        chars = paragraph.toCharArray();
        int start = 0;
        int max = 0;
        int maxStart = 0;
        int maxEnd = chars.length;
        boolean alpha = false;
        int tmp;
        for (int i = 0; i <= chars.length; i++) {
            if (i < chars.length && chars[i] >= 'a' && chars[i] <= 'z') {
                if (!alpha) {
                    start = i;
                }
                alpha = true;
            } else if (i < chars.length && chars[i] >= 'A' && chars[i] <= 'Z') {
                if (!alpha) {
                    start = i;
                }
                alpha = true;
                chars[i] += 32;
            } else {
                if (alpha) {
                    if (i == chars.length) {
                        tmp = incrAndGet(root, chars, start, i - 1, false);
                    } else {
                        tmp = incrAndGet(root, chars, start, i, false);
                    }
                    if (tmp > max) {
                        max = tmp;
                        maxStart = start;
                        maxEnd = i;
                    }
                }

                alpha = false;
            }
        }

        return paragraph.substring(maxStart, maxEnd).toLowerCase();
    }

    private int incrAndGet(Node root, char[] keyArr, int start, int end, boolean banned) {
        int idx;
        for (int i = start; i < end; i++) {
            idx = keyArr[i] - 'a';
            if (root.next[idx] == null) {
                root.next[idx] = new Node();
            }
            root = root.next[idx];
        }

        if (banned) {
            root.value = -1;
        }
        if (root.value >= 0) {
            root.value += 1;
        }

        return root.value;
    }
}
