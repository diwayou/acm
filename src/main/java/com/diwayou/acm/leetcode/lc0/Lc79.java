package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/word-search/
 *
 * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 *
 * 示例:
 * board =
 * [
 *   ['A','B','C','E'],
 *   ['S','F','C','S'],
 *   ['A','D','E','E']
 * ]
 *
 * 给定 word = "ABCCED", 返回 true.
 * 给定 word = "SEE", 返回 true.
 * 给定 word = "ABCB", 返回 false.
 */
public class Lc79 {

    private int m;
    private int n;
    private char[][] b;
    private char[] w;

    public boolean exist(char[][] board, String word) {
        if (word.isEmpty()) {
            return true;
        }
        if (board.length == 0) {
            return false;
        }

        m = board.length;
        n = board[0].length;
        b = board;
        w = word.toCharArray();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(int i, int j, int k) {
        if (b[i][j] != w[k]) {
            return false;
        }

        if (k == w.length - 1) {
            return true;
        }

        char v = b[i][j];
        b[i][j] = '0';

        if (i > 0 && dfs(i - 1, j, k + 1)) {
            return true;
        }
        if (i < m - 1 && dfs(i + 1, j, k + 1)) {
            return true;
        }
        if (j > 0 && dfs(i, j - 1, k + 1)) {
            return true;
        }
        if (j < n - 1 && dfs(i, j + 1, k + 1)) {
            return true;
        }

        b[i][j] = v;
        return false;
    }
}
