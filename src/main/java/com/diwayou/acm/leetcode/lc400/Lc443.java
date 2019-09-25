package com.diwayou.acm.leetcode.lc400;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/string-compression/
 */
public class Lc443 {
    public static void main(String[] args) {
        char[] chars = new char[]{'a', 'a', 'b', 'b', 'b', 'c'};
        System.out.println(new Lc443().compress(chars));
        System.out.println(Arrays.toString(chars));
    }

    public int compress(char[] chars) {
        if (chars.length == 1) {
            return 1;
        }

        int j = 0;
        int s = 0;
        char cur;
        int cnt;
        for (int i = 1; i <= chars.length; i++) {
            if (i == chars.length || chars[i] != chars[i - 1]) {
                cur = chars[s];
                cnt = i - s;

                if (cnt > 1) {
                    chars[j++] = cur;
                    if (cnt >= 100) {
                        chars[j++] = (char) ('0' + cnt / 100);
                        cnt %= 100;
                        chars[j++] = (char) ('0' + cnt / 10);
                        cnt %= 10;
                        chars[j++] = (char) ('0' + cnt);
                    } else if (cnt >= 10) {
                        chars[j++] = (char) ('0' + cnt / 10);
                        cnt %= 10;
                        chars[j++] = (char) ('0' + cnt);
                    } else {
                        chars[j++] = (char) ('0' + cnt);
                    }
                } else {
                    chars[j++] = cur;
                }

                s = i;
            }
        }

        return j;
    }
}
