package com.diwayou.game.gobang.entity;

import java.awt.*;

public class ChessBoard {

    /**
     * 棋盘单元格的长度
     */
    private int row = 30;

    /**
     * 棋盘每行第一个点与左侧的距离
     */
    private int margin = 20;

    /**
     * 每行15个落子点
     */
    private int rowper = 15;

    /**
     * 棋子半径
     */
    private int chessRadius = 13;

    /**
     * 字体
     */
    private Font font = new Font("楷体", Font.PLAIN, 13);

    /**
     * 棋盘背景颜色
     */
    private Color bgColor = new Color(246, 214, 159);

    /**
     * 棋盘线颜色
     */
    private Color lineColor = new Color(164, 135, 81);

    /**
     * 星的颜色
     */
    private Color pointColor = new Color(116, 88, 49);

    /**
     * 棋盘
     */
    private ChessOwner[][] chess;

    public ChessBoard() {
        restart();
    }

    public void restart() {
        chess = new ChessOwner[rowper][rowper];
    }

    public boolean play(int x, int y, ChessOwner owner) {
        if (x < 0 || x >= rowper) {
            return false;
        }
        if (y < 0 || y >= rowper) {
            return false;
        }
        if (chess[x][y] != null) {
            return false;
        }

        chess[x][y] = owner;

        return true;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getRowper() {
        return rowper;
    }

    public void setRowper(int rowper) {
        this.rowper = rowper;
    }

    public int getChessRadius() {
        return chessRadius;
    }

    public void setChessRadius(int chessRadius) {
        this.chessRadius = chessRadius;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getPointColor() {
        return pointColor;
    }

    public void setPointColor(Color pointColor) {
        this.pointColor = pointColor;
    }

    public ChessOwner[][] getChess() {
        return chess;
    }

    public boolean isWin(int x, int y, ChessOwner cur) {
        // 四个方向上的连子数
        int count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        int i, j;

        //横向扫描
        for (j = y; j < chess.length; j++) {
            if (chess[x][j] == cur)
                count1++;
            else
                break;
        }
        for (j = y - 1; j >= 0; j--) {
            if (chess[x][j] == cur)
                count1++;
            else
                break;
        }
        if (count1 >= 5)
            return true;

        //纵向扫描
        for (i = x; i < chess.length; i++) {
            if (chess[i][y] == cur)
                count2++;
            else
                break;
        }
        for (i = x - 1; i >= 0; i--) {
            if (chess[i][y] == cur)
                count2++;
            else
                break;
        }
        if (count2 >= 5)
            return true;

        //正斜向扫描
        for (i = x, j = y; i < chess.length && j < chess.length; i++, j++) {
            if (chess[i][j] == cur)
                count3++;
            else
                break;
        }
        for (i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (chess[i][j] == cur)
                count3++;
            else
                break;
        }
        if (count3 >= 5)
            return true;

        //反斜向扫描
        for (i = x, j = y; i < chess.length && j >= 0; i++, j--) {
            if (chess[i][j] == cur)
                count4++;
            else
                break;
        }
        for (i = x - 1, j = y + 1; i >= 0 && j < chess.length; i--, j++) {
            if (chess[i][j] == cur)
                count4++;
            else
                break;
        }
        if (count4 >= 5)
            return true;

        return false;
    }
}
