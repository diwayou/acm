package com.diwayou.acm.leetcode.lc1000;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/occurrences-after-bigram/
 *
 * 给出第一个词first 和第二个词second，考虑在某些文本text中可能以 "first second third" 形式出现的情况，其中second紧随first出现，third紧随second出现。
 * 对于每种这样的情况，将第三个词 "third" 添加到答案中，并返回答案。
 *
 * 示例 1：
 * 输入：text = "alice is a good girl she is a good student", first = "a", second = "good"
 * 输出：["girl","student"]
 *
 * 示例 2：
 * 输入：text = "we will we will rock you", first = "we", second = "will"
 * 输出：["we","rock"]
 *
 * 提示：
 * 1 <= text.length <= 1000
 * text由一些用空格分隔的单词组成，每个单词都由小写英文字母组成
 * 1 <= first.length, second.length <= 10
 * first 和second由小写英文字母组成
 */
public class Lc1078 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc1078().findOcurrences("jkypmsxd jkypmsxd kcyxdfnoa jkypmsxd kcyxdfnoa jkypmsxd kcyxdfnoa kcyxdfnoa jkypmsxd kcyxdfnoa",
                "kcyxdfnoa",
                "jkypmsxd")));
    }

    public String[] findOcurrences(String text, String first, String second) {
        String[] ta = text.split(" ");
        if (ta.length < 3) {
            return new String[0];
        }

        int c = 0;
        List<String> re = new ArrayList<>();
        for (int i = 0; i < ta.length - 2; i++) {
            if (ta[i].equals(first) && ta[i + 1].equals(second)) {
                re.add(ta[i + 2]);
            }
        }

        return re.toArray(new String[0]);
    }
}
