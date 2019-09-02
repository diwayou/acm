package com.diwayou.acm.team;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode-cn.com/problems/time-based-key-value-store/
 *
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
public class TimeBasedKeyValueStore {
    private Map<String, TreeMap<Integer, String>> kv = new HashMap<>();

    public TimeBasedKeyValueStore() {
    }

    public void set(String key, String value, int timestamp) {
        TreeMap<Integer, String> v = kv.computeIfAbsent(key, k -> new TreeMap<>());
        v.put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer, String> v = kv.get(key);
        if (v == null) {
            return "";
        }

        Map.Entry<Integer, String> e =  v.floorEntry(timestamp);
        if (e == null) {
            return "";
        }

        return e.getValue();
    }
}
