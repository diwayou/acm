package com.diwayou.game.gobang.core;

import com.diwayou.game.gobang.entity.ChessBoard;
import com.diwayou.game.gobang.entity.ChessOwner;
import com.diwayou.game.gobang.ui.ChessBoardUi;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class ChessController {

    private ChessBoard chessBoard;

    private ChessBoardUi chessBoardUi;

    private ChessOwner curUser;

    private List<PlayListener> playListeners;

    public ChessController(ChessBoard chessBoard, ChessBoardUi chessBoardUi) {
        this.chessBoard = chessBoard;
        this.chessBoardUi = chessBoardUi;

        restartBoard();
    }

    /**
     * 棋局重开事件处理函数
     */
    public void restartBoard() {
        chessBoard.restart();//棋盘控制器初始化棋盘
        chessBoardUi.clearBoard();//棋盘view清除棋子重绘
        curUser = ChessOwner.playerOne;

        notifyListeners();
    }

    /**
     * 棋盘面板的鼠标点击事件
     *
     * @param e
     */
    public void showChess(MouseEvent e) {
        //点击的位置坐标
        int x = e.getX();
        int y = e.getY();

        //转化为棋盘上的行列值
        int col = (x - 5) / 30;
        int row = (y - 5) / 30;

        showChess(row, col);
    }

    public void showChess(int row, int col) {
        //玩家落子有效
        boolean isEnable = chessBoard.play(row, col, curUser);
        if (isEnable) {
            // 棋盘面板绘制棋子
            chessBoardUi.doPlay(row, col, curUser);

            //玩家胜利
            if (chessBoard.isWin(row, col, curUser)) {
                JOptionPane.showMessageDialog(chessBoardUi, curUser.name() + "获胜", "恭喜胜利了！", JOptionPane.WARNING_MESSAGE);
                restartBoard();//初始化棋盘
                return;
            }

            curUser = curUser.equals(ChessOwner.playerOne) ? ChessOwner.playerTwo : ChessOwner.playerOne;

            notifyListeners();
        } else {
            System.out.println("坐标无效!");
        }
    }

    public void registerListeners(List<PlayListener> playListeners) {
        this.playListeners = playListeners;
    }

    private void notifyListeners() {
        if (this.playListeners == null) {
            return;
        }

        for (PlayListener listener : playListeners) {
            listener.play(curUser);
        }
    }
}
