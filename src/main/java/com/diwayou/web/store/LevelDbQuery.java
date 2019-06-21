package com.diwayou.web.store;

public class LevelDbQuery {

    private byte[] offset;

    private int namespace;

    public LevelDbQuery() {
    }

    public byte[] getOffset() {
        return offset;
    }

    public LevelDbQuery setOffset(byte[] offset) {
        this.offset = offset;
        return this;
    }

    public int getNamespace() {
        return namespace;
    }

    public LevelDbQuery setNamespace(int namespace) {
        this.namespace = namespace;
        return this;
    }
}
