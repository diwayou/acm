package com.diwayou.acm.http;

import org.openqa.selenium.WebDriver;

import java.util.function.Function;

/**
 * 判断页面是否加载完成
 */
@FunctionalInterface
public interface PageLoadReady extends Function<WebDriver, Boolean> {
}
