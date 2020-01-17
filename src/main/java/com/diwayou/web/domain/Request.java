package com.diwayou.web.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Request {

    public static final byte MIN_PRIORITY = 100;
    public static final byte NORMAL_PRIORITY = 50;
    public static final byte MAX_PRIORITY = 0;

    public static final Request empty = new Request("").setPriority(MAX_PRIORITY);

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
    private String parentUrl = "";

    /**
     * 爬取最大深度
     */
    private int maxDepth = 2;

    /**
     * 当前抓取深度
     */
    private int depth;

    /**
     * 抓取优先级
     */
    private byte priority = NORMAL_PRIORITY;

    /**
     * 扩展信息
     */
    private Map<String, Object> attributes;

    private List<RequestScript> requestScripts;

    public Request() {
    }

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

    public int getDepth() {
        return depth;
    }

    public Request setDepth(int depth) {
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    private Request setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public Request addAttribute(String key, Object value) {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }

        this.attributes.put(key, value);

        return this;
    }

    public Object getAttribute(String key) {
        return this.attributes == null ? null : this.attributes.get(key);
    }

    public List<RequestScript> getRequestScripts() {
        return requestScripts;
    }

    public Request setRequestScripts(List<RequestScript> requestScripts) {
        this.requestScripts = requestScripts;
        return this;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public Request setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
        return this;
    }

    public boolean needCrawl() {
        return depth <= maxDepth;
    }

    public Request copy() {
        return new Request(url)
                .setParentUrl(parentUrl)
                .setMaxDepth(maxDepth)
                .setDepth(depth)
                .setPriority(priority)
                .setAttributes(attributes)
                .setFetcherType(fetcherType)
                .setTimeout(timeout)
                .setRequestScripts(requestScripts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return url.equals(request.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", fetcherType=" + fetcherType +
                ", timeout=" + timeout +
                ", parentUrl='" + parentUrl + '\'' +
                ", maxDepth=" + maxDepth +
                ", depth=" + depth +
                ", priority=" + priority +
                ", attributes=" + attributes +
                ", requestScripts=" + requestScripts +
                '}';
    }
}
