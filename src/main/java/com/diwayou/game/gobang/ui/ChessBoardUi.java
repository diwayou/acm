package com.diwayou.game.gobang.ui;

import com.diwayou.game.gobang.entity.ChessBoard;
import com.diwayou.game.gobang.entity.ChessOwner;
import com.diwayou.game.gobang.entity.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardUi extends JPanel {

    private ChessBoard chessBoard;

    private List<ChessPieceUi> chessPieceUi;

    public ChessBoardUi(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.chessPieceUi = new ArrayList<>(chessBoard.getRowper() * chessBoard.getRowper());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(chessBoard.getFont());
        //设置抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBoard(g2);
        drawChessman(g2);
    }

    private void drawChessman(Graphics2D g2) {
        // 得到FontMetrics对象, 主要为了设置字体居中
        FontMetrics metrics = g2.getFontMetrics();

        // 遍历棋局绘制棋子
        for (ChessPieceUi chessPiece : chessPieceUi) {
            chessPiece.draw(g2, metrics);
        }
    }

    private void drawBoard(Graphics2D g2) {
        g2.setColor(chessBoard.getBgColor());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        g2.setColor(chessBoard.getLineColor());

        //画棋盘线
        int margin = chessBoard.getMargin();
        int offset, wMargin = this.getWidth() - margin, hMargin = this.getHeight() - margin;
        int rowper = chessBoard.getRowper();
        int row = chessBoard.getRow();
        for (int i = 0; i < rowper; i++) {
            offset = margin + row * i;
            g2.drawLine(margin, offset, wMargin, offset);
            g2.drawLine(offset, margin, offset, hMargin);
        }

        //设置颜色以及画棋盘上的点
        //10是圆的半径
        g2.setColor(chessBoard.getPointColor());
        int a = margin - 5 + 3 * row, b = margin - 5 + 7 * row, c = margin - 5 + 11 * row;
        g2.fillOval(a, a, 10, 10);
        g2.fillOval(b, b, 10, 10);
        g2.fillOval(a, c, 10, 10);
        g2.fillOval(c, a, 10, 10);
        g2.fillOval(c, c, 10, 10);
    }

    /**
     * 清除棋盘
     */
    public void clearBoard() {
        chessPieceUi.clear();

        repaint();
    }

    /**
     * 视图上落子
     */
    public void doPlay(int row, int col, ChessOwner owner) {
        chessPieceUi.add(new ChessPieceUi(new ChessPiece(row, col, owner), chessBoard, chessPieceUi.size() + 1));

        repaint();
    }
}
