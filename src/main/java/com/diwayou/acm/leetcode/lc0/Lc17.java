package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 * <p>
 * 2 abc
 * 3 def
 * 4 ghi
 * 5 jkl
 * 6 mno
 * 7 pqrs
 * 8 tuv
 * 9 wxyz
 */
public class Lc17 {

    public static void main(String[] args) {
        System.out.println(new Lc17().letterCombinations("23"));
    }

    private static char[][] alpha = {
            {},
            {},
            {'a', 'b', 'c'},
            {'d', 'e', 'f'},
            {'g', 'h', 'i'},
            {'j', 'k', 'l'},
            {'m', 'n', 'o'},
            {'p', 'q', 'r', 's'},
            {'t', 'u', 'v'},
            {'w', 'x', 'y', 'z'}
    };

    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> re = new ArrayList<>();

        char[] d = digits.toCharArray();
        char[] cur = new char[d.length];

        letterCombinationsHelper(d, 0, cur, re);

        return re;
    }

    private void letterCombinationsHelper(char[] d, int idx, char[] cur, List<String> re) {
        if (idx == d.length) {
            re.add(new String(cur));
            return;
        }

        char[] a = alpha[d[idx] - '0'];
        for (int i = 0; i < a.length; i++) {
            cur[idx] = a[i];
            letterCombinationsHelper(d, idx + 1, cur, re);
        }
    }
}
