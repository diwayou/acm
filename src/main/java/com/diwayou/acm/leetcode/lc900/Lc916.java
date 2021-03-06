package com.diwayou.acm.leetcode.lc900;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/word-subsets/
 * <p>
 * 我们给出两个单词数组 A和B。每个单词都是一串小写字母。
 * 现在，如果b 中的每个字母都出现在 a 中，包括重复出现的字母，那么称单词 b 是单词 a 的子集。 例如，“wrr” 是 “warrior” 的子集，但不是 “world” 的子集。
 * 如果对 B 中的每一个单词b，b 都是 a 的子集，那么我们称A 中的单词 a 是通用的。
 * 你可以按任意顺序以列表形式返回A 中所有的通用单词。
 * <p>
 * 示例 1：
 * 输入：A = ["amazon","apple","facebook","google","leetcode"], B = ["e","o"]
 * 输出：["facebook","google","leetcode"]
 * <p>
 * 示例 2：
 * 输入：A = ["amazon","apple","facebook","google","leetcode"], B = ["l","e"]
 * 输出：["apple","google","leetcode"]
 * <p>
 * 示例 3：
 * 输入：A = ["amazon","apple","facebook","google","leetcode"], B = ["e","oo"]
 * 输出：["facebook","google"]
 * <p>
 * 示例 4：
 * 输入：A = ["amazon","apple","facebook","google","leetcode"], B = ["lo","eo"]
 * 输出：["google","leetcode"]
 * <p>
 * 示例 5：
 * 输入：A = ["amazon","apple","facebook","google","leetcode"], B = ["ec","oc","ceo"]
 * 输出：["facebook","leetcode"]
 * <p>
 * 提示：
 * 1 <= A.length, B.length <= 10000
 * 1 <= A[i].length, B[i].length<= 10
 * A[i]和B[i]只由小写字母组成。
 * A[i]中所有的单词都是独一无二的，也就是说不存在i != j使得A[i] == A[j]。
 */
public class Lc916 {

    public List<String> wordSubsets(String[] A, String[] B) {
        int[] cnt = new int[26];
        for (String s : B) {
            int[] c = new int[26];
            for (int i = 0; i < s.length(); i++) {
                c[s.charAt(i) - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                cnt[i] = Math.max(cnt[i], c[i]);
            }
        }

        List<String> re = new ArrayList<>();
        for (String s : A) {
            int[] c = new int[26];
            for (int i = 0; i < s.length(); i++) {
                c[s.charAt(i) - 'a']++;
            }
            int i = 0;
            for (; i < 26; i++) {
                if (c[i] < cnt[i]) {
                    break;
                }
            }
            if (i == 26) {
                re.add(s);
            }
        }

        return re;
    }
}
