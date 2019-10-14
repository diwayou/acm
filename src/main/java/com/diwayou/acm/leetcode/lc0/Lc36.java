package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/valid-sudoku/
 */
public class Lc36 {

    public boolean isValidSudoku(char[][] board) {
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] block = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                
                int num = board[i][j] - '1';
                int blockIndex = i / 3 * 3 + j / 3;
                if (row[i][num] || col[j][num] || block[blockIndex][num]) {
                    return false;
                } else {
                    row[i][num] = true;
                    col[j][num] = true;
                    block[blockIndex][num] = true;
                }
            }
        }

        return true;
    }

    public boolean isValidSudoku1(char[][] board) {
        int[] rowCnt = new int[9];
        int[] colCnt = new int[9];
        int[] boxCnt = new int[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ('.' == board[i][j]) {
                    continue;
                }

                int num = board[i][j] - 48;
                // 处理行
                if ((rowCnt[i] >> num) % 2 == 1) {
                    return false;
                } else {
                    rowCnt[i] += 1 << num;
                }
                // 处理列
                if ((colCnt[j] >> num) % 2 == 1) {
                    return false;
                } else {
                    colCnt[j] += 1 << num;
                }
                // 处理框
                int boxNum = i / 3 * 3 + j / 3;
                if ((boxCnt[boxNum] >> num) % 2 == 1) {
                    return false;
                } else {
                    boxCnt[boxNum] += 1 << num;
                }
            }
        }

        return true;
    }
}
