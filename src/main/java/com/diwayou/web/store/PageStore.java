package com.diwayou.web.store;

import com.diwayou.web.domain.Page;

public interface PageStore {

    void store(Page page, PageStoreContext context);
}
