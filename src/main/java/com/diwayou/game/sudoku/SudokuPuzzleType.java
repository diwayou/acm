package com.diwayou.game.sudoku;

public enum SudokuPuzzleType {
    FOUR_BY_FOUR(4, 4, 2, 2, 0.44444, new String[]{"1", "2", "3", "4"}, "4 by 4 Game"),
    SIX_BY_SIX(6, 6, 3, 2, 0.22222, new String[]{"1", "2", "3", "4", "5", "6"}, "6 By 6 Game"),
    NINE_BY_NINE(9, 9, 3, 3, 0.22222, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, "9 By 9 Game"),
    TWELVE_BY_TWELVE(12, 12, 4, 3, 0.22222, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C"}, "12 By 12 Game"),
    SIXTEEN_BY_SIXTEEN(16, 16, 4, 4, 0.22222, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G"}, "16 By 16 Game");

    private final int rows;
    private final int columns;
    private final int boxWidth;
    private final int boxHeight;
    private final double rand;
    private final String[] validValues;
    private final String desc;

    private SudokuPuzzleType(int rows, int columns, int boxWidth, int boxHeight, double rand, String[] validValues, String desc) {
        this.rows = rows;
        this.columns = columns;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.rand = rand;
        this.validValues = validValues;
        this.desc = desc;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public double getRand() {
        return rand;
    }

    public String[] getValidValues() {
        return validValues;
    }

    public String toString() {
        return desc;
    }
}
