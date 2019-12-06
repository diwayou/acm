package com.diwayou.acm.leetcode.lc700;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/letter-case-permutation/
 *
 * 给定一个字符串S，通过将字符串S中的每个字母转变大小写，我们可以获得一个新的字符串。返回所有可能得到的字符串集合。
 *
 * 示例:
 * 输入: S = "a1b2"
 * 输出: ["a1b2", "a1B2", "A1b2", "A1B2"]
 *
 * 输入: S = "3z4"
 * 输出: ["3z4", "3Z4"]
 *
 * 输入: S = "12345"
 * 输出: ["12345"]
 *
 * 注意：
 * S 的长度不超过12。
 * S 仅由数字和字母组成。
 */
public class Lc784 {

    public static void main(String[] args) {
        System.out.println(new Lc784().letterCasePermutation("1"));
    }

    public List<String> letterCasePermutation(String S) {
        List<String> re = new ArrayList<>();
        dfs(S.toCharArray(), 0, re);
        return re;
    }

    public void dfs(char[] s, int u, List<String> ans) {
        if (u == s.length) {
            ans.add(String.valueOf(s));
            return;
        }
        //对于a1b2 就是从a开始
        dfs(s, u + 1, ans);
        if (s[u] >= 'A') {
            //'A' 65 1000001
            //'a' 97 1100001
            //从A开始
            s[u] ^= (1 << 5);//1000001（65）^100000（32）=1100001（97）  1100001（97）^100000（32）=1000001（65） 小写字母转换为大写字母 大写字母转换为小写字母
            dfs(s, u + 1, ans);
        }
    }
}
