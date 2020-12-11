package com.diwayou.acm.leetcode.lc800;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/uncommon-words-from-two-sentences/
 * <p>
 * 给定两个句子A和B。（句子是一串由空格分隔的单词。每个单词仅由小写字母组成。）
 * 如果一个单词在其中一个句子中只出现一次，在另一个句子中却没有出现，那么这个单词就是不常见的。
 * 返回所有不常用单词的列表。
 * 您可以按任何顺序返回列表。
 * <p>
 * 示例 1：
 * 输入：A = "this apple is sweet", B = "this apple is sour"
 * 输出：["sweet","sour"]
 * <p>
 * 示例2：
 * 输入：A = "apple apple", B = "banana"
 * 输出：["banana"]
 * <p>
 * 提示：
 * 0 <= A.length <= 200
 * 0 <= B.length <= 200
 * A 和B都只包含空格和小写字母。
 */
public class Lc884 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc884().uncommonFromSentences("apple apple", "banana")));
    }

    public String[] uncommonFromSentences(String A, String B) {
        Map<String, Integer> ma = buildMap(A);
        Map<String, Integer> mb = buildMap(B);

        List<String> re = new ArrayList<>();
        match(ma, mb, re);
        match(mb, ma, re);

        return re.toArray(new String[0]);
    }

    private void match(Map<String, Integer> m1, Map<String, Integer> m2, List<String> re) {
        for (Map.Entry<String, Integer> e : m1.entrySet()) {
            if (e.getValue() == 1 && !m2.containsKey(e.getKey())) {
                re.add(e.getKey());
            }
        }
    }

    private static Map<String, Integer> buildMap(String ss) {
        Map<String, Integer> m = new HashMap<>();
        for (String s : ss.split(" ")) {
            Integer c = m.get(s);
            if (c == null) {
                m.put(s, 1);
            } else {
                m.put(s, c + 1);
            }
        }

        return m;
    }

    public String[] uncommonFromSentences1(String A, String B) {
        Map<String, Integer> count = new HashMap<>();
        for (String word : A.split(" "))
            count.put(word, count.getOrDefault(word, 0) + 1);
        for (String word : B.split(" "))
            count.put(word, count.getOrDefault(word, 0) + 1);

        List<String> ans = new LinkedList<>();
        for (String word : count.keySet())
            if (count.get(word) == 1)
                ans.add(word);

        return ans.toArray(new String[0]);
    }
}
