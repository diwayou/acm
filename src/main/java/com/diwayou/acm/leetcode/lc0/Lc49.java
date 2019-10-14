package com.diwayou.acm.leetcode.lc0;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/group-anagrams/
 */
public class Lc49 {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> m = new HashMap<>();

        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String ts = new String(ca);
            List<String> v = m.get(ts);
            if (v == null) {
                v = new ArrayList<>();
                m.put(ts, v);
            }
            v.add(s);
        }

        return new ArrayList<>(m.values());
    }
}
