package com.diwayou.web.script;

import com.diwayou.web.domain.Page;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;

import javax.script.*;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 要执行的脚本都在该类中注册，根据对域名匹配度进行执行处理
 */
public class ScriptRegistry {

    private static final Logger log = Logger.getLogger(ScriptRegistry.class.getName());

    public static final String DOMAIN_ALL = "*";

    private static ScriptRegistry instance = new ScriptRegistry();

    private DoubleArrayTrie<CrawlScript> domainScripTrie = new DoubleArrayTrie<>();

    private CrawlScript defaultScript;

    private ScriptEngine scriptEngine;

    private Compilable compilable;

    public ScriptRegistry() {
        this.scriptEngine = new ScriptEngineManager().getEngineByName("groovy");
        this.compilable = (Compilable) scriptEngine;
    }

    public CrawlScript match(Page page) {
        if (domainScripTrie == null || domainScripTrie.getSize() == 0) {
            return defaultScript;
        }

        try {
            URI uri = URI.create(page.getRequest().getUrl());
            String host = uri.getHost();
            if (host == null) {
                return defaultScript;
            }

            if (host.startsWith("www.")) {
                host = host.substring(4);
            }

            return domainScripTrie.get(host);
        } catch (Exception e) {
            log.log(Level.WARNING, "", e);
        }

        return defaultScript;
    }

    public Bindings createBindings() {
        return this.scriptEngine.createBindings();
    }

    /**
     * 加载域名适用的解析脚本
     * 例如www.baidu.com需要注册baidu.com，不包含www.
     */
    public void load(TreeMap<String, CrawlScript> domainScriptMap) throws ScriptException {
        if (domainScriptMap == null || domainScriptMap.isEmpty()) {
            return;
        }

        for (Map.Entry<String, CrawlScript> entry : domainScriptMap.entrySet()) {
            compile(entry.getValue());
        }

        this.defaultScript = domainScriptMap.get(DOMAIN_ALL);
        if (defaultScript != null) {
            domainScriptMap.remove(DOMAIN_ALL);
        }

        if (!domainScriptMap.isEmpty()) {
            domainScripTrie.build(domainScriptMap);
        }
    }

    private CrawlScript compile(CrawlScript script) throws ScriptException {
        if (script == null || script.getSrc() == null) {
            return script;
        }

        return script.setCompiledScript(compilable.compile(script.getSrc()));
    }

    public static ScriptRegistry one() {
        return instance;
    }
}
