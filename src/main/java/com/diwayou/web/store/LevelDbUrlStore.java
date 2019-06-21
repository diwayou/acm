package com.diwayou.web.store;

import org.rocksdb.RocksDBException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelDbUrlStore implements UrlStore {

    private static final Logger log = Logger.getLogger("");

    private LevelDbStore requestStore;

    private static final int URL_NAMESPACE = 1;

    public LevelDbUrlStore(Path storePath) {
        try {
            this.requestStore = new LevelDbStore(storePath.resolve("urlVisited").toFile());
        } catch (RocksDBException e) {
            log.log(Level.WARNING, "", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean contain(String url) {
        try {
            return requestStore.get(URL_NAMESPACE, url.getBytes(StandardCharsets.UTF_8)) != null;
        } catch (RocksDBException e) {
            log.log(Level.WARNING, "", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String url) {
        try {
            requestStore.write(wb -> {
                wb.put(URL_NAMESPACE, url.getBytes(StandardCharsets.UTF_8), new byte[]{0});
            });
        } catch (RocksDBException e) {
            log.log(Level.WARNING, "", e);
            throw new RuntimeException(e);
        }
    }
}
