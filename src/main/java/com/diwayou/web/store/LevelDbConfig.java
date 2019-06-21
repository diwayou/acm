package com.diwayou.web.store;

public class LevelDbConfig {

    private boolean useExisting = false;

    private int writeBufferSize;

    private boolean sync = false;

    private boolean disableWAL = true;

    public boolean isUseExisting() {
        return useExisting;
    }

    public LevelDbConfig setUseExisting(boolean useExisting) {
        this.useExisting = useExisting;
        return this;
    }

    public int getWriteBufferSize() {
        return writeBufferSize;
    }

    public LevelDbConfig setWriteBufferSize(int writeBufferSize) {
        this.writeBufferSize = writeBufferSize;
        return this;
    }

    public boolean isSync() {
        return sync;
    }

    public LevelDbConfig setSync(boolean sync) {
        this.sync = sync;
        return this;
    }

    public boolean isDisableWAL() {
        return disableWAL;
    }

    public LevelDbConfig setDisableWAL(boolean disableWAL) {
        this.disableWAL = disableWAL;
        return this;
    }
}
