package com.diwayou.web.store;

import org.iq80.leveldb.ReadOptions;
import org.iq80.leveldb.Snapshot;

public class LevelDbQuery {

    private byte[] offset;

    private byte[] namespace;

    private ReadOptions options;

    public LevelDbQuery() {
        this.options = new ReadOptions();
    }

    public byte[] getOffset() {
        return offset;
    }

    public LevelDbQuery setOffset(byte[] offset) {
        this.offset = offset;
        return this;
    }

    public byte[] getNamespace() {
        return namespace;
    }

    public LevelDbQuery setNamespace(byte[] namespace) {
        this.namespace = namespace;
        return this;
    }

    public ReadOptions getOptions() {
        return options;
    }

    public LevelDbQuery setOptions(ReadOptions options) {
        this.options = options;
        return this;
    }

    public boolean isVerifyChecksums() {
        return this.options.verifyChecksums();
    }

    public LevelDbQuery setVerifyChecksums(boolean verifyChecksums) {
        this.options.verifyChecksums(verifyChecksums);
        return this;
    }

    public boolean isFillCache() {
        return this.options.fillCache();
    }

    public LevelDbQuery setFillCache(boolean fillCache) {
        this.options.fillCache(fillCache);
        return this;
    }

    public Snapshot getSnapshot() {
        return this.options.snapshot();
    }

    public LevelDbQuery setSnapshot(Snapshot snapshot) {
        this.options.snapshot(snapshot);
        return this;
    }
}
