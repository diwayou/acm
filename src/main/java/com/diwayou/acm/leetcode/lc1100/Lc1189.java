package com.diwayou.acm.leetcode.lc1100;

/**
 * https://leetcode-cn.com/problems/maximum-number-of-balloons/
 *
 * 给你一个字符串text，你需要使用 text 中的字母来拼凑尽可能多的单词"balloon"（气球）。
 * 字符串text 中的每个字母最多只能被使用一次。请你返回最多可以拼凑出多少个单词"balloon"。
 *
 * 示例 1：
 * 输入：text = "nlaebolko"
 * 输出：1
 *
 * 示例 2：
 * 输入：text = "loonbalxballpoon"
 * 输出：2
 *
 * 示例 3：
 * 输入：text = "leetcode"
 * 输出：0
 *
 * 提示：
 * 1 <= text.length <= 10^4
 * text全部由小写英文字母组成
 */
public class Lc1189 {

    public static void main(String[] args) {
        System.out.println(new Lc1189().maxNumberOfBalloons("leetcode"));
    }

    public int maxNumberOfBalloons(String text) {
        int[] cnt = new int[26];
        for (char c : text.toCharArray()) {
            cnt[c - 'a']++;
        }

        cnt['l' - 'a'] /= 2;
        cnt['o' - 'a'] /= 2;
        int re = cnt[0];
        for (char c : "blon".toCharArray()) {
            re = Math.min(re, cnt[c - 'a']);
        }

        return re;
    }
}
