package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/wildcard-matching/
 *
 * 给定一个字符串(s) 和一个字符模式(p) ，实现一个支持'?'和'*'的通配符匹配。
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符串（包括空字符串）。
 * 两个字符串完全匹配才算匹配成功。
 *
 * 说明:
 * s可能为空，且只包含从a-z的小写字母。
 * p可能为空，且只包含从a-z的小写字母，以及字符?和*。
 *
 * 示例1:
 * 输入:
 * s = "aa"
 * p = "a"
 * 输出: false
 * 解释: "a" 无法匹配 "aa" 整个字符串。
 *
 * 示例2:
 * 输入:
 * s = "aa"
 * p = "*"
 * 输出: true
 * 解释:'*' 可以匹配任意字符串。
 *
 * 示例3:
 * 输入:
 * s = "cb"
 * p = "?a"
 * 输出: false
 * 解释:'?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
 *
 * 示例4:
 * 输入:
 * s = "adceb"
 * p = "*a*b"
 * 输出: true
 * 解释:第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
 *
 * 示例5:
 * 输入:
 * s = "acdcb"
 * p = "a*c?b"
 * 输入: false
 */
public class Lc44 {

    public static void main(String[] args) {
        System.out.println(new Lc44().isMatch("aa", "a"));
        System.out.println(new Lc44().isMatch("aa", "*"));
        System.out.println(new Lc44().isMatch("cb", "?a"));
        System.out.println(new Lc44().isMatch("adceb", "*a*b"));
        System.out.println(new Lc44().isMatch("acdcb", "a*c?b"));
    }

    public boolean isMatch(String s, String p) {
        char[] sc = s.toCharArray();
        char[] pc = ("(" + p.replace("*", ".*") + ")").toCharArray();

        int m = pc.length + 1;
        boolean[][] graph = new boolean[m][m];
        for (int i = 0; i < m - 1; i++) {
            if (pc[i] == '*') {
                graph[i - 1][i] = true;
                graph[i][i - 1] = true;
                graph[i][i + 1] = true;
            } else if (pc[i] == ')' || pc[i] == '(') {
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

                    if (pc[j] == sc[i] || pc[j] == '?' || pc[j] == '.') {
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

    public boolean isMatch1(String s, String p) {
        int sn = s.length();
        int pn = p.length();
        int i = 0;
        int j = 0;
        int start = -1;
        int match = 0;
        while (i < sn) {
            if (j < pn && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
                i++;
                j++;
            } else if (j < pn && p.charAt(j) == '*') {
                start = j;
                match = i;
                j++;
            } else if (start != -1) {
                j = start + 1;
                match++;
                i = match;
            } else {
                return false;
            }
        }

        while (j < pn) {
            if (p.charAt(j) != '*') return false;
            j++;
        }

        return true;
    }
}
