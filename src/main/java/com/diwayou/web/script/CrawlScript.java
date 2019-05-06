package com.diwayou.web.script;

import javax.script.CompiledScript;

public class CrawlScript {

    private String src;

    private CompiledScript compiledScript;

    public CrawlScript(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public CrawlScript setSrc(String src) {
        this.src = src;
        return this;
    }

    public CompiledScript getCompiledScript() {
        return compiledScript;
    }

    public CrawlScript setCompiledScript(CompiledScript compiledScript) {
        this.compiledScript = compiledScript;
        return this;
    }
}
