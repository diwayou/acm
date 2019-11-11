package com.diwayou.acm.leetcode.lc200;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/word-pattern/
 *
 * 给定一种规律 pattern 和一个字符串 str ，判断 str 是否遵循相同的规律。
 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之间存在着双向连接的对应规律。
 *
 * 示例1:
 * 输入: pattern = "abba", str = "dog cat cat dog"
 * 输出: true
 *
 * 示例 2:
 * 输入:pattern = "abba", str = "dog cat cat fish"
 * 输出: false
 *
 * 示例 3:
 * 输入: pattern = "aaaa", str = "dog cat cat dog"
 * 输出: false
 *
 * 示例 4:
 * 输入: pattern = "abba", str = "dog dog dog dog"
 * 输出: false
 *
 * 说明:
 * 你可以假设 pattern 只包含小写字母， str 包含了由单个空格分隔的小写字母。
 */
public class Lc290 {

    public boolean wordPattern(String pattern, String str) {
        char[] ca = pattern.toCharArray();
        String[] sa = str.split(" ");
        if (ca.length != sa.length) {
            return false;
        }

        Map<Character, String> m = new HashMap<>();
        // 可以换成Set
        Set<String> rm = new HashSet<>();
        for (int i = 0; i < ca.length; i++) {
            String s = m.get(ca[i]);
            if (s == null) {
                m.put(ca[i], sa[i]);
                if (rm.contains(sa[i])) {
                    return false;
                } else {
                    rm.add(sa[i]);
                }
            } else if (!s.equals(sa[i])) {
                return false;
            }
        }

        return true;
    }
}
