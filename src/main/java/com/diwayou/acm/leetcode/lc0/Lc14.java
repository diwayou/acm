package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/longest-common-prefix/
 */
public class Lc14 {

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }

        StringBuilder re = new StringBuilder();
        int idx = 0;
        char ch;
        while (true) {
            if (idx >= strs[0].length()) {
                return re.toString();
            }

            ch = strs[0].charAt(idx);
            for (int i = 1; i < strs.length; i++) {
                if (idx >= strs[i].length() || strs[i].charAt(idx) != ch) {
                    return re.toString();
                }
            }

            re.append(ch);
            idx++;
        }
    }
}
