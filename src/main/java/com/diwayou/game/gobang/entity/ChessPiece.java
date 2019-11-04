package com.diwayou.game.gobang.entity;

public class ChessPiece {

    public static final ChessPiece empty = new ChessPiece();

    private int x;

    private int y;

    private ChessOwner owner;

    public ChessPiece() {
    }

    public ChessPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChessPiece(int x, int y, ChessOwner owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ChessOwner getOwner() {
        return owner;
    }

    public void setOwner(ChessOwner owner) {
        this.owner = owner;
    }
}
