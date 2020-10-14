package com.diwayou.acm.leetcode.lc500;

/**
 * https://leetcode-cn.com/problems/detect-capital/
 *
 * 给定一个单词，你需要判断单词的大写使用是否正确。
 * 我们定义，在以下情况时，单词的大写用法是正确的：
 * 全部字母都是大写，比如"USA"。
 * 单词中所有字母都不是大写，比如"leetcode"。
 * 如果单词不只含有一个字母，只有首字母大写，比如"Google"。
 * 否则，我们定义这个单词没有正确使用大写字母。
 *
 * 示例 1:
 * 输入: "USA"
 * 输出: True
 *
 * 示例 2:
 * 输入: "FlaG"
 * 输出: False
 * 注意: 输入是由大写和小写拉丁字母组成的非空单词。
 */
public class Lc520 {

    public static void main(String[] args) {
        System.out.println(new Lc520().detectCapitalUse("a"));
    }

    public boolean detectCapitalUse(String word) {
        boolean s = false;
        int bc = 0;
        for (char c : word.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                if (!s) {
                    s = true;
                }
                if (bc > 1) {
                    return false;
                }
            } else {
                bc++;
                if (s) {
                    return false;
                }
            }
        }

        return true;
    }
}
