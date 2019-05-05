package com.diwayou.web.script;

import com.diwayou.web.domain.Page;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;

import java.net.URI;
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

    public CrawlScript match(Page page) {
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

    /**
     * 加载域名适用的解析脚本
     * 例如www.baidu.com需要注册baidu.com，不包含www.
     */
    public void load(TreeMap<String, CrawlScript> domainScriptMap) {
        this.defaultScript = domainScriptMap.get(DOMAIN_ALL);
        if (defaultScript != null) {
            domainScriptMap.remove(DOMAIN_ALL);
        }

        domainScripTrie.build(domainScriptMap);
    }

    public static ScriptRegistry one() {
        return instance;
    }
}
