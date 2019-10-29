package com.diwayou.game.gobang.ui;

import com.diwayou.game.gobang.entity.ChessBoard;
import com.diwayou.game.gobang.entity.ChessOwner;
import com.diwayou.game.gobang.entity.ChessPiece;

import java.awt.*;

public class ChessPieceUi {

    private ChessPiece chessPiece;

    private ChessBoard chessBoard;

    /**
     * 棋子在棋盘上落子的顺序编号
     */
    private int num;

    public ChessPieceUi(ChessPiece chessPiece, ChessBoard chessBoard, int num) {
        this.chessPiece = chessPiece;
        this.chessBoard = chessBoard;
        this.num = num;
    }

    public void draw(Graphics2D g2, FontMetrics metrics) {
        if (chessPiece.getOwner().equals(ChessOwner.playerOne)) {
            g2.setColor(Color.black);
        } else {
            g2.setColor(Color.white);
        }

        int margin = chessBoard.getMargin(), row = chessBoard.getRow(), chessRadius = chessBoard.getChessRadius();
        //画棋子
        g2.fillOval(margin - 13 + chessPiece.getY() * row, margin - chessRadius + chessPiece.getX() * row, chessRadius * 2, chessRadius * 2);

        // 画棋子上的数字
        if (chessPiece.getOwner().equals(ChessOwner.playerOne)) {
            g2.setColor(Color.white);
        } else {
            g2.setColor(Color.black);
        }

        String strNum = String.valueOf(num);
        int ascent = metrics.getAscent();
        int descent = metrics.getDescent();
        // 计算字符串应在的坐标
        g2.drawString(strNum, margin + chessPiece.getY() * row - metrics.stringWidth(strNum) / 2, margin + chessPiece.getX() * row - (ascent + descent) / 2 + ascent);
    }
}
