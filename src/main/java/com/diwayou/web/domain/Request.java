package com.diwayou.web.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Request {

    public static final Request empty = new Request("");

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
    private int depth;

    /**
     * 抓取优先级
     */
    private int priority;

    /**
     * 扩展信息
     */
    private Map<String, String> attributes;

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

    public int getPriority() {
        return priority;
    }

    public Request setPriority(int priority) {
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

    public List<RequestScript> getRequestScripts() {
        return requestScripts;
    }

    public Request setRequestScripts(List<RequestScript> requestScripts) {
        this.requestScripts = requestScripts;
        return this;
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
                ", depth=" + depth +
                ", priority=" + priority +
                ", attributes=" + attributes +
                '}';
    }
}
