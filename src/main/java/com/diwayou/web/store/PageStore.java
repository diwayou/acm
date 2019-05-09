package com.diwayou.web.store;

import com.diwayou.web.domain.Page;

public interface PageStore {

    StoreResult store(Page page, PageStoreContext context);
}
