package com.diwayou.lang.string;

import com.google.common.collect.Maps;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;

import java.util.TreeMap;

public class TrieStudy {
    public static void main(String[] args) {
        TreeMap<String, String> domains = Maps.newTreeMap();
        domains.put("www.baidu.com", "百度");
        domains.put("www.163.com", "网易");
        DoubleArrayTrie<String> trie = new DoubleArrayTrie<>();
        trie.build(domains);

        System.out.println(trie.commonPrefixSearch("www.baidu.com.abc"));
    }
}
