package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/battleships-in-a-board/
 */
public class Lc419 {

    public int countBattleships(char[][] board) {
        if (board.length == 0) {
            return 0;
        }
        if (board[0].length == 0) {
            return 0;
        }

        int r = board.length, c = board[0].length;

        int re = 0, k;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == '.') {
                    continue;
                }

                re++;
                board[i][j] = '.';
                k = i + 1;
                while (k < r && board[k][j] == 'X') {
                    board[k][j] = '.';
                    k++;
                }
                k = j + 1;
                while (k < c && board[i][k] == 'X') {
                    board[i][k] = '.';
                    k++;
                }
            }
        }

        return re;
    }

    public int countBattleships1(char[][] board) {
        int count = 0, i, j;

        for (i = 0; i < board.length; ++i) {
            for (j = 0; j < board[i].length; ++j) {
                if ((board[i][j] == 'X') && (i == 0 || board[i - 1][j] == '.') && (j == 0 || board[i][j - 1] == '.'))
                    ++count;
            }
        }

        return count;
    }
}
