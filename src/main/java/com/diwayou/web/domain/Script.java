package com.diwayou.web.domain;

public class Script {

    private String src;

    private int timeout;

    public Script(String src, int timeout) {
        this.src = src;
        this.timeout = timeout;
    }

    public String getSrc() {
        return src;
    }

    public Script setSrc(String src) {
        this.src = src;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public Script setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
}
