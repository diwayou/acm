package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/available-captures-for-rook/
 *
 * 在一个 8 x 8 的棋盘上，有一个白色车（rook）。也可能有空方块，白色的象（bishop）和黑色的卒（pawn）。它们分别以字符 “R”，“.”，“B” 和 “p” 给出。大写字符表示白棋，小写字符表示黑棋。
 * 车按国际象棋中的规则移动：它选择四个基本方向中的一个（北，东，西和南），然后朝那个方向移动，直到它选择停止、到达棋盘的边缘或移动到同一方格来捕获该方格上颜色相反的卒。另外，车不能与其他友方（白色）象进入同一个方格。
 * 返回车能够在一次移动中捕获到的卒的数量。
 *  
 * 提示：
 * board.length == board[i].length == 8
 * board[i][j] 可以是 'R'，'.'，'B' 或 'p'
 * 只有一个格子上存在 board[i][j] == 'R'
 */
public class Lc999 {

    public int numRookCaptures(char[][] board) {
        int re = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'R') {
                    int t = i - 1;
                    while (t >= 0 && board[t][j] == '.') {
                        t--;
                    }
                    if (t >= 0 && board[t][j] == 'p') {
                        re++;
                    }

                    t = i + 1;
                    while (t < 8 && board[t][j] == '.') {
                        t++;
                    }
                    if (t < 8 && board[t][j] == 'p') {
                        re++;
                    }

                    t = j - 1;
                    while (t >= 0 && board[i][t] == '.') {
                        t--;
                    }
                    if (t >= 0 && board[i][t] == 'p') {
                        re++;
                    }

                    t = j + 1;
                    while (t < 8 && board[i][t] == '.') {
                        t++;
                    }
                    if (t < 8 && board[i][t] == 'p') {
                        re++;
                    }

                    return re;
                }
            }
        }

        return re;
    }
}
