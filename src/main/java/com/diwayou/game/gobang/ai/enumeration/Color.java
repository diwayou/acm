package com.diwayou.game.gobang.ai.enumeration;

public enum Color {
    BLACK, WHITE, NULL;

    public Color getOtherColor() {
        if (this == NULL) {
            return null;
        }
        return this == BLACK ? WHITE : BLACK;
    }
}
