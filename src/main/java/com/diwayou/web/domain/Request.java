package com.diwayou.web.domain;

import java.util.Map;

public class Request {

    /**
     * 当前抓取地址
     */
    private String url;

    /**
     * 该网址抓取类型
     */
    private FetcherType fetcherType = FetcherType.JAVA_HTTP;

    /**
     * 抓取超时时间，单位：秒
     */
    private int timeout = 3;

    /**
     * 父网址
     */
    private String parentUrl;

    /**
     * 当前抓取深度
     */
    private short depth;

    /**
     * 抓取优先级
     */
    private byte priority;

    /**
     * 扩展信息
     */
    private Map<String, String> attributes;

    public Request(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    public FetcherType getFetcherType() {
        return fetcherType;
    }

    public Request setFetcherType(FetcherType fetcherType) {
        this.fetcherType = fetcherType;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public Request setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public Request setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
        return this;
    }

    public short getDepth() {
        return depth;
    }

    public Request setDepth(short depth) {
        this.depth = depth;
        return this;
    }

    public byte getPriority() {
        return priority;
    }

    public Request setPriority(byte priority) {
        this.priority = priority;
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public Request setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }
}
