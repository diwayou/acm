package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/number-of-segments-in-a-string/
 * <p>
 * 统计字符串中的单词个数，这里的单词指的是连续的不是空格的字符。
 * 请注意，你可以假定字符串里不包括任何不可打印的字符。
 * <p>
 * 示例:
 * 输入: "Hello, my name is John"
 * 输出: 5
 */
public class Lc434 {

    public int countSegments(String s) {
        if (s.isEmpty()) {
            return 0;
        }

        int re = 0;
        boolean has = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (has) {
                    re++;
                    has = false;
                }
            } else {
                has = true;
            }
        }

        return has ? re + 1 : re;
    }
}
