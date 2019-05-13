package com.diwayou.web.store;

import com.google.common.collect.Lists;
import org.iq80.leveldb.*;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.iq80.leveldb.util.Closeables;
import org.iq80.leveldb.util.FileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
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

    public void write(Consumer<WriteBatch> callback) throws IOException {
        write(callback, false);
    }

    public void write(Consumer<WriteBatch> callback, boolean sync) throws IOException {
        write(callback, sync, false);
    }

    public Snapshot write(Consumer<WriteBatch> callback, boolean sync, boolean snapshot) throws IOException {
        try (WriteBatch batch = db.createWriteBatch()) {
            callback.accept(batch);

            return db.write(batch, new WriteOptions().sync(sync).snapshot(snapshot));
        }
    }

    public List<Entry<byte[], byte[]>> query(StoreQuery<LevelDbQuery> storeQuery) {
        LevelDbQuery query = storeQuery.getQuery();
        DBIterator iterator = db.iterator(query.getOptions());
        if (query.getOffset() != null) {
            iterator.seek(query.getOffset());
        } else {
            iterator.seekToFirst();
        }

        List<Entry<byte[], byte[]>> result = Lists.newArrayListWithCapacity(storeQuery.getPageSize());
        int pageSize = storeQuery.getPageSize();
        while (iterator.hasNext() && pageSize-- > 0) {
            Map.Entry<byte[], byte[]> next = iterator.next();

            byte[] namespace = new byte[]{next.getKey()[0]};
            if (!Arrays.equals(namespace, query.getNamespace())) {
                break;
            }

            result.add(next);
        }

        return result;
    }

    public byte[] get(byte[] key) {
        return db.get(key);
    }

    public byte[] get(byte[] key, ReadOptions options) {
        return db.get(key, options);
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
