package com.diwayou.web.domain;

public class RequestScript {

    private String src;

    private int timeout;

    public RequestScript(String src, int timeout) {
        this.src = src;
        this.timeout = timeout;
    }

    public String getSrc() {
        return src;
    }

    public RequestScript setSrc(String src) {
        this.src = src;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public RequestScript setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
}
