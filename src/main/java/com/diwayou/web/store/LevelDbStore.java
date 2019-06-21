package com.diwayou.web.store;

import com.diwayou.web.log.AppLog;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.rocksdb.*;

import java.io.Closeable;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class LevelDbStore implements Closeable {

    private static final Logger log = AppLog.getCrawl();

    static {
        RocksDB.loadLibrary();
    }

    private LevelDbConfig config;

    private File databaseDir;

    private RocksDB db;

    private Options options;

    private WriteOptions writeOptions;

    public LevelDbStore(File databaseDir) throws RocksDBException {
        this(databaseDir, new LevelDbConfig());
    }

    public LevelDbStore(File databaseDir, LevelDbConfig config) throws RocksDBException {
        this.databaseDir = databaseDir;
        this.config = config;

        this.writeOptions = new WriteOptions();
        this.writeOptions.setSync(config.isSync());
        // If `sync` is true, `disableWAL` must be set false.
        this.writeOptions.setDisableWAL(!config.isSync() && config.isDisableWAL());

        if (!config.isUseExisting()) {
            destroyDb();
        }

        open();
    }

    private void open()
            throws RocksDBException {
        options = new Options();
        options.setCreateIfMissing(true);

        this.db = RocksDB.open(this.options, this.databaseDir.getAbsolutePath());
    }

    public void write(Consumer<LevelDbWriteCallback> callback) throws RocksDBException {
        write(callback, false);
    }

    public void write(Consumer<LevelDbWriteCallback> callback, boolean sync) throws RocksDBException {
        write(callback, sync, false);
    }

    public void write(Consumer<LevelDbWriteCallback> callback, boolean sync, boolean snapshot) throws RocksDBException {
        try (WriteBatch batch = new WriteBatch()) {

            callback.accept(new LevelDbWriteCallback(batch));

            db.write(writeOptions, batch);
        }
    }

    public List<Entry<byte[], byte[]>> query(StoreQuery<LevelDbQuery> storeQuery) {
        LevelDbQuery query = storeQuery.getQuery();
        try (final ReadOptions readOptions = new ReadOptions()) {
            try (RocksIterator iterator = db.newIterator(readOptions)) {
                if (query.getOffset() != null) {
                    iterator.seek(genKey(query.getNamespace(), query.getOffset()));
                } else {
                    iterator.seekToFirst();
                }

                List<Entry<byte[], byte[]>> result = Lists.newArrayListWithCapacity(storeQuery.getPageSize());
                int pageSize = storeQuery.getPageSize();
                while (iterator.isValid() && pageSize-- > 0) {
                    iterator.next();

                    if (!Arrays.equals(iterator.key(), 0, Ints.BYTES, Ints.toByteArray(query.getNamespace()), 0, Ints.BYTES)) {
                        break;
                    }

                    result.add(Map.entry(iterator.key(), iterator.value()));
                }

                return result;
            }
        }
    }

    public byte[] get(int namespace, byte[] key) throws RocksDBException {
        return db.get(genKey(namespace, key));
    }

    static byte[] genKey(int namespace, byte[] key) {
        ByteBuffer buffer = ByteBuffer.allocate(Ints.BYTES + key.length);
        buffer.putInt(namespace);
        buffer.put(key);

        return buffer.array();
    }

    private void destroyDb() throws RocksDBException {
        try (final Options opt = new Options()) {
            RocksDB.destroyDB(databaseDir.getAbsolutePath(), opt);
        }
    }

    @Override
    public void close() {
        if (this.db != null) {
            this.db.close();
            this.db = null;
        }

        if (this.options != null) {
            this.options.close();
            this.options = null;
        }

        if (this.writeOptions != null) {
            this.writeOptions.close();
            this.writeOptions = null;
        }
    }
}
