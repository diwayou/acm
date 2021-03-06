package com.diwayou.acm.leetcode.lc1100;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/compare-strings-by-frequency-of-the-smallest-character/
 * <p>
 * 我们来定义一个函数f(s)，其中传入参数s是一个非空字符串；该函数的功能是统计s 中（按字典序比较）最小字母的出现频次。
 * 例如，若s = "dcce"，那么f(s) = 2，因为最小的字母是"c"，它出现了2 次。
 * 现在，给你两个字符串数组待查表queries和词汇表words，请你返回一个整数数组answer作为答案，其中每个answer[i]是满足f(queries[i])< f(W)的词的数目，W是词汇表words中的词。
 * <p>
 * 示例 1：
 * 输入：queries = ["cbd"], words = ["zaaaz"]
 * 输出：[1]
 * 解释：查询 f("cbd") = 1，而 f("zaaaz") = 3 所以 f("cbd") < f("zaaaz")。
 * <p>
 * 示例 2：
 * 输入：queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
 * 输出：[1,2]
 * 解释：第一个查询 f("bbb") < f("aaaa")，第二个查询 f("aaa") 和 f("aaaa") 都 > f("cc")。
 * <p>
 * 提示：
 * 1 <= queries.length <= 2000
 * 1 <= words.length <= 2000
 * 1 <= queries[i].length, words[i].length <= 10
 * queries[i][j], words[i][j]都是小写英文字母
 */
public class Lc1170 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc1170().numSmallerByFrequency(new String[]{"bbb", "cc"}, new String[]{"a", "aa", "aaa", "aaaa"})));
    }

    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] cnts = new int[11];
        for (String w : words) {
            cnts[f(w)]++;
        }
        int sum = 0, t;
        for (int i = cnts.length - 1; i > 0; i--) {
            t = cnts[i];
            cnts[i] = sum;
            sum += t;
        }

        int[] re = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            re[i] = cnts[f(queries[i])];
        }

        return re;
    }

    private static int f(String s) {
        char min = 'z';
        int cnt = 0;
        for (char c : s.toCharArray()) {
            if (c < min) {
                min = c;
                cnt = 1;
            } else if (c == min) {
                cnt++;
            }
        }

        return cnt;
    }
}
