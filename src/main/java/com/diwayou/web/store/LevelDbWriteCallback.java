package com.diwayou.web.store;

import org.rocksdb.RocksDBException;
import org.rocksdb.WriteBatch;

public class LevelDbWriteCallback {

    private WriteBatch writeBatch;

    public LevelDbWriteCallback(WriteBatch writeBatch) {
        this.writeBatch = writeBatch;
    }

    public void put(int namespace, byte[] key, byte[] value) {
        try {
            writeBatch.put(LevelDbStore.genKey(namespace, key), value);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int namespace, byte[] key) {
        try {
            writeBatch.delete(LevelDbStore.genKey(namespace, key));
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }
}
