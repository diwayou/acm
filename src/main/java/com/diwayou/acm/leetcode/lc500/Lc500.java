package com.diwayou.acm.leetcode.lc500;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/keyboard-row/
 * <p>
 * 给定一个单词列表，只返回可以使用在键盘同一行的字母打印出来的单词。键盘如下图所示。
 * <p>
 * 示例：
 * 输入: ["Hello", "Alaska", "Dad", "Peace"]
 * 输出: ["Alaska", "Dad"]
 * <p>
 * 注意：
 * 你可以重复使用键盘上同一字符。
 * 你可以假设输入的字符串将只包含字母。
 */
public class Lc500 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc500().findWords(new String[]{"Hello", "Alaska", "Dad", "Peace"})));
    }

    private static final int[] key = {2, 3, 3, 2, 1, 2, 2, 2, 1, 2, 2, 2, 3, 3, 1, 1, 1, 1, 2, 1, 1, 3, 1, 3, 1, 3};

    public String[] findWords(String[] words) {
        List<String> re = new ArrayList<>(words.length);
        for (String s : words) {
            char[] ch = s.toCharArray();
            int f = key[toLower(ch[0]) - 'a'], i;
            for (i = 1; i < ch.length; i++) {
                if (key[toLower(ch[i]) - 'a'] != f) {
                    break;
                }
            }

            if (i == ch.length) {
                re.add(s);
            }
        }

        return re.toArray(new String[0]);
    }

    private static char toLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 'a' - 'A');
        }

        return c;
    }
}
