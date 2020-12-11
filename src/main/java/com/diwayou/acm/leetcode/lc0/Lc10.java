package com.diwayou.acm.leetcode.lc0;

import java.util.regex.Pattern;

/**
 * https://leetcode-cn.com/problems/regular-expression-matching/
 * <p>
 * 给你一个字符串s和一个字符规律p，请你来实现一个支持 '.'和'*'的正则表达式匹配。
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖整个字符串s的，而不是部分字符串。
 * <p>
 * 说明:
 * s可能为空，且只包含从a-z的小写字母。
 * p可能为空，且只包含从a-z的小写字母，以及字符.和*。
 * <p>
 * 示例 1:
 * 输入:
 * s = "aa"
 * p = "a"
 * 输出: false
 * 解释: "a" 无法匹配 "aa" 整个字符串。
 * <p>
 * 示例 2:
 * 输入:
 * s = "aa"
 * p = "a*"
 * 输出: true
 * 解释:因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
 * <p>
 * 示例3:
 * 输入:
 * s = "ab"
 * p = ".*"
 * 输出: true
 * 解释:".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
 * <p>
 * 示例 4:
 * 输入:
 * s = "aab"
 * p = "c*a*b"
 * 输出: true
 * 解释:因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
 * <p>
 * 示例 5:
 * 输入:
 * s = "mississippi"
 * p = "mis*is*p*."
 * 输出: false
 */
public class Lc10 {

    public static void main(String[] args) {
        System.out.println(new Lc10().isMatch("aa", "a"));
        System.out.println(new Lc10().isMatch("aaa", "aaaa"));
        System.out.println(new Lc10().isMatch("aa", "a*"));
        System.out.println(new Lc10().isMatch("ab", ".*"));
        System.out.println(new Lc10().isMatch("ab", ".*c"));
        System.out.println(new Lc10().isMatch("aab", "c*a*b"));
        System.out.println(new Lc10().isMatch("mississippi", "mis*is*p*."));
    }

    public boolean isMatch(String s, String p) {
        return Pattern.matches(p, s);
    }

    public boolean isMatch1(String s, String p) {
        char[] sc = s.toCharArray();
        char[] pc = (p + ")").toCharArray();

        int m = pc.length + 1;
        boolean[][] graph = new boolean[m][m];
        for (int i = 0; i < m - 1; i++) {
            if (pc[i] == '*') {
                graph[i - 1][i] = true;
                graph[i][i - 1] = true;
                graph[i][i + 1] = true;
            } else if (pc[i] == ')') {
                graph[i][i + 1] = true;
            }
        }

        boolean[] mark = dfs(graph, 0);
        for (int i = 0; i < sc.length; i++) {
            boolean[] match = new boolean[m];
            for (int j = 0; j < m; j++) {
                if (mark[j]) {
                    if (j == m - 1) {
                        continue;
                    }

                    if (pc[j] == sc[i] || pc[j] == '.') {
                        match[j + 1] = true;
                    }
                }
            }

            mark = dfs(graph, match);
        }

        return mark[m - 1];
    }

    private boolean[] dfs(boolean[][] graph, int s) {
        boolean[] re = new boolean[graph.length];
        doDfs(graph, re, s);

        return re;
    }

    private boolean[] dfs(boolean[][] graph, boolean[] mark) {
        boolean[] re = new boolean[graph.length];
        for (int i = 0; i < mark.length; i++) {
            if (mark[i]) {
                doDfs(graph, re, i);
            }
        }

        return re;
    }

    private void doDfs(boolean[][] graph, boolean[] mark, int v) {
        mark[v] = true;
        for (int i = 0; i < graph.length; i++) {
            if (!mark[i] && graph[v][i]) {
                doDfs(graph, mark, i);
            }
        }
    }
}
