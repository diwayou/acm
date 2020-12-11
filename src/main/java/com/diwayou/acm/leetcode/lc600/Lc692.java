package com.diwayou.acm.leetcode.lc600;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/top-k-frequent-words/
 * <p>
 * 给一非空的单词列表，返回前k个出现次数最多的单词。
 * 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率，按字母顺序排序。
 * <p>
 * 示例 1：
 * 输入: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 * 输出: ["i", "love"]
 * 解析: "i" 和 "love" 为出现次数最多的两个单词，均为2次。
 * 注意，按字母顺序 "i" 在 "love" 之前。
 * <p>
 * 示例 2：
 * 输入: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * 输出: ["the", "is", "sunny", "day"]
 * 解析: "the", "is", "sunny" 和 "day" 是出现次数最多的四个单词，
 * 出现次数依次为 4, 3, 2 和 1 次。
 * <p>
 * 注意：
 * 假定 k 总为有效值， 1 ≤ k ≤ 集合元素数。
 * 输入的单词均由小写字母组成。
 * <p>
 * 扩展练习：
 * 尝试以O(n log k) 时间复杂度和O(n) 空间复杂度解决。
 */
public class Lc692 {

    public static void main(String[] args) {
        System.out.println(new Lc692().topKFrequent(new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}, 4));
    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> cnts = new HashMap<>();
        for (String s : words) {
            cnts.put(s, cnts.getOrDefault(s, 0) + 1);
        }

        Comparator<Map.Entry<String, Integer>> cmp = new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue().equals(o2.getValue())) {
                    return o2.getKey().compareTo(o1.getKey());
                } else {
                    return o1.getValue().compareTo(o2.getValue());
                }
            }
        };

        PriorityQueue<Map.Entry<String, Integer>> q = new PriorityQueue<>(k + 1, cmp);

        for (Map.Entry<String, Integer> e : cnts.entrySet()) {
            q.offer(e);

            if (q.size() > k) {
                q.poll();
            }
        }

        LinkedList<String> re = new LinkedList<>();
        while (!q.isEmpty()) {
            re.addFirst(q.poll().getKey());
        }

        return re;
    }
}
