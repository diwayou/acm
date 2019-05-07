package com.diwayou.web.script;

import com.diwayou.web.domain.Page;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

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

    private ScriptRegistry() {
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

    public Object execute(Page page, Map<String, Object> bindings) {
        CrawlScript crawlScript = match(page);
        if (crawlScript == null) {
            log.warning("没有配置处理脚本url=" + page.getRequest().getUrl());
            return null;
        }

        if (crawlScript.getScriptFile() != null) {
            try {
                return new GroovyShell(new Binding(bindings)).evaluate(crawlScript.getScriptFile());
            } catch (Exception e) {
                log.log(Level.WARNING, "", e);
            }
        } else if (crawlScript.getSrc() != null) {
            try {
                Bindings bind = createBindings();
                bind.putAll(bindings);

                return crawlScript.getCompiledScript().eval(bind);
            } catch (ScriptException e) {
                log.log(Level.WARNING, "", e);
            }
        } else {
            log.warning("脚本没有内容url=" + page.getRequest().getUrl());

            return null;
        }

        return null;
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
