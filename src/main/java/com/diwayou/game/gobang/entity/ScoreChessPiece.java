package com.diwayou.game.gobang.entity;

public class ScoreChessPiece extends ChessPiece {

    private int score;

    public ScoreChessPiece(int x, int y) {
        super(x, y);
    }

    public ScoreChessPiece(int x, int y, ChessOwner owner) {
        super(x, y, owner);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
