package com.diwayou.web.store;

import org.iq80.leveldb.WriteBatch;

public class LevelDbWriteCallback {

    private WriteBatch writeBatch;

    public LevelDbWriteCallback(WriteBatch writeBatch) {
        this.writeBatch = writeBatch;
    }

    public WriteBatch put(int namespace, byte[] key, byte[] value) {
        return writeBatch.put(LevelDbStore.genKey(namespace, key), value);
    }

    public WriteBatch delete(int namespace, byte[] key) {
        return writeBatch.delete(LevelDbStore.genKey(namespace, key));
    }
}
