package com.diwayou.web.script;

import com.diwayou.web.domain.Page;
import com.diwayou.web.log.AppLog;
import com.diwayou.web.support.FilenameUtil;
import com.diwayou.web.url.UrlUtil;
import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import javax.script.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 要执行的脚本都在该类中注册，根据对域名匹配度进行执行处理
 */
public class ScriptRegistry {

    private static final Logger log = AppLog.getCrawl();

    public static final String DOMAIN_ALL = "All";

    private static ScriptRegistry instance = new ScriptRegistry();

    private ConcurrentMap<String, CrawlScript> domainScripMap = Maps.newConcurrentMap();

    private CrawlScript defaultScript;

    private ScriptEngine scriptEngine;

    private Compilable compilable;

    private ScriptRegistry() {
        this.scriptEngine = new ScriptEngineManager().getEngineByName("groovy");
        this.compilable = (Compilable) scriptEngine;
    }

    public CrawlScript match(Page page) {
        if (domainScripMap == null || domainScripMap.isEmpty()) {
            return defaultScript;
        }

        try {
            String host = UrlUtil.getHost(page.getRequest().getUrl());

            CrawlScript script = domainScripMap.get(host);
            if (script != null) {
                return script;
            }
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

    public Object execute(String script, Map<String, Object> bindings) {
        try {
            return new GroovyShell(new Binding(bindings)).evaluate(script);
        } catch (Exception e) {
            log.log(Level.WARNING, "", e);
        }

        return null;
    }

    /**
     * 加载目录下的所有脚本，文件名就是注册的域名
     */
    public void load(File dir) throws IOException, ScriptException {
        if (dir == null || !dir.isDirectory()) {
            throw new RuntimeException("脚本目录格式不正确!");
        }

        TreeMap<String, CrawlScript> domainScriptMap = Files.list(dir.toPath())
                .map(this::pathToScriptPair)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, TreeMap::new));

        load(domainScriptMap);
    }

    private Map.Entry<String, CrawlScript> pathToScriptPair(Path path) {
        String domain = FilenameUtil.getNameWithoutExt(path.toString()).replace("_", ".");

        return new AbstractMap.SimpleImmutableEntry<>(domain, new CrawlScript(path.toFile()));
    }

    /**
     * 加载域名适用的解析脚本
     * 例如www.baidu.com需要注册baidu.com，不包含www.
     */
    public void load(TreeMap<String, CrawlScript> domainScriptMap) throws ScriptException {
        if (domainScriptMap == null || domainScriptMap.isEmpty()) {
            return;
        }

        for (Entry<String, CrawlScript> entry : domainScriptMap.entrySet()) {
            compile(entry.getValue());
        }

        this.defaultScript = domainScriptMap.get(DOMAIN_ALL);
        if (defaultScript != null) {
            domainScriptMap.remove(DOMAIN_ALL);
        }

        if (!domainScriptMap.isEmpty()) {
            domainScripMap.putAll(domainScriptMap);
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
