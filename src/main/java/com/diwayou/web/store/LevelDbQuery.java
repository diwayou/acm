package com.diwayou.web.store;

import org.iq80.leveldb.ReadOptions;
import org.iq80.leveldb.Snapshot;
import org.iq80.leveldb.util.Slice;

public class LevelDbQuery {

    private Slice offset;

    private ReadOptions options;

    public LevelDbQuery() {
        this.options = new ReadOptions();
    }

    public Slice getOffset() {
        return offset;
    }

    public LevelDbQuery setOffset(Slice offset) {
        this.offset = offset;
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
