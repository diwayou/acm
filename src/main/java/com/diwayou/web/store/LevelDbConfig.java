package com.diwayou.web.store;

public class LevelDbConfig {

    private boolean useExisting;

    private int writeBufferSize;

    public boolean isUseExisting() {
        return useExisting;
    }

    public LevelDbConfig setUseExisting(boolean useExisting) {
        this.useExisting = useExisting;
        return this;
    }
}
