package com.diwayou.web.script;

import javax.script.CompiledScript;
import java.io.File;

public class CrawlScript {

    private String src;

    private CompiledScript compiledScript;

    private File scriptFile;

    public CrawlScript(File scriptFile) {
        this.scriptFile = scriptFile;
    }

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

    public File getScriptFile() {
        return scriptFile;
    }

    public CrawlScript setScriptFile(File scriptFile) {
        this.scriptFile = scriptFile;
        return this;
    }
}
