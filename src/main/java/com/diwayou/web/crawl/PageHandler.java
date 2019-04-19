package com.diwayou.web.crawl;

import com.diwayou.web.domain.Page;

public interface PageHandler {

    void handle(Page page, Spider spider);
}
