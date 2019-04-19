package com.diwayou.web.fetcher;

import com.diwayou.web.domain.FetcherType;
import com.diwayou.web.domain.Page;
import com.diwayou.web.domain.Request;

import java.io.Closeable;

public interface Fetcher extends Closeable {

    Page fetch(Request request);

    FetcherType getType();
}
