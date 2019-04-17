package com.diwayou.web.http.robot;

import java.util.function.Function;

/**
 * 判断页面是否加载完成
 */
@FunctionalInterface
public interface PageLoadReady<T> extends Function<T, Boolean> {
}
