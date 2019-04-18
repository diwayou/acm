package com.diwayou.web.crawl;

import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;

public interface PageHandler {

    boolean interest(Request request);

    void handle(Page page, Spider spider);
}
