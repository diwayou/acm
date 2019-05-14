package com.diwayou.web.store;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.iq80.leveldb.*;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.iq80.leveldb.util.Closeables;
import org.iq80.leveldb.util.FileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class LevelDbStore implements Closeable {

    private LevelDbConfig config;

    private File databaseDir;

    private DBFactory factory;

    private DB db;

    public LevelDbStore(File databaseDir) throws IOException {
        this(databaseDir, new LevelDbConfig());
    }

    public LevelDbStore(File databaseDir, LevelDbConfig config) throws IOException {
        this.factory = new Iq80DBFactory();
        this.databaseDir = databaseDir;
        this.config = config;

        if (!config.isUseExisting()) {
            destroyDb();
        }

        open();
    }

    private void open()
            throws IOException {
        Options options = new Options();
        options.createIfMissing(!config.isUseExisting());

        db = factory.open(databaseDir, options);
    }

    public void write(Consumer<LevelDbWriteCallback> callback) throws IOException {
        write(callback, false);
    }

    public void write(Consumer<LevelDbWriteCallback> callback, boolean sync) throws IOException {
        write(callback, sync, false);
    }

    public Snapshot write(Consumer<LevelDbWriteCallback> callback, boolean sync, boolean snapshot) throws IOException {
        try (WriteBatch batch = db.createWriteBatch()) {

            callback.accept(new LevelDbWriteCallback(batch));

            return db.write(batch, new WriteOptions().sync(sync).snapshot(snapshot));
        }
    }

    public List<Entry<byte[], byte[]>> query(StoreQuery<LevelDbQuery> storeQuery) {
        LevelDbQuery query = storeQuery.getQuery();
        DBIterator iterator = db.iterator(query.getOptions());
        if (query.getOffset() != null) {
            iterator.seek(genKey(query.getNamespace(), query.getOffset()));
        } else {
            iterator.seekToFirst();
        }

        List<Entry<byte[], byte[]>> result = Lists.newArrayListWithCapacity(storeQuery.getPageSize());
        int pageSize = storeQuery.getPageSize();
        while (iterator.hasNext() && pageSize-- > 0) {
            Map.Entry<byte[], byte[]> next = iterator.next();

            if (!Arrays.equals(next.getKey(), 0, Ints.BYTES, Ints.toByteArray(query.getNamespace()), 0, Ints.BYTES)) {
                break;
            }

            result.add(next);
        }

        return result;
    }

    public byte[] get(int namespace, byte[] key) {
        return db.get(genKey(namespace, key));
    }

    public byte[] get(int namespace, byte[] key, ReadOptions options) {
        return db.get(genKey(namespace, key), options);
    }

    static byte[] genKey(int namespace, byte[] key) {
        ByteBuffer buffer = ByteBuffer.allocate(Ints.BYTES + key.length);
        buffer.putInt(namespace);
        buffer.put(key);

        return buffer.array();
    }

    private void destroyDb() {
        Closeables.closeQuietly(db);
        db = null;
        FileUtils.deleteRecursively(databaseDir);
    }

    @Override
    public void close() throws IOException {
        this.db.close();
    }
}
