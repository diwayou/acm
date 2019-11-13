package com.diwayou.acm.leetcode.lc700;

/**
 * https://leetcode-cn.com/problems/design-hashmap/
 *
 * 不使用任何内建的哈希表库设计一个哈希映射
 * 具体地说，你的设计应该包含以下的功能
 * put(key, value)：向哈希映射中插入(键,值)的数值对。如果键对应的值已经存在，更新这个值。
 * get(key)：返回给定的键所对应的值，如果映射中不包含这个键，返回-1。
 * remove(key)：如果映射中存在这个键，删除这个数值对。
 *
 * 示例：
 *
 * MyHashMap hashMap = new MyHashMap();
 * hashMap.put(1, 1);          
 * hashMap.put(2, 2);        
 * hashMap.get(1);            // 返回 1
 * hashMap.get(3);            // 返回 -1 (未找到)
 * hashMap.put(2, 1);         // 更新已有的值
 * hashMap.get(2);            // 返回 1
 * hashMap.remove(2);         // 删除键为2的数据
 * hashMap.get(2);            // 返回 -1 (未找到)
 *
 * 注意：
 * 所有的值都在 [1, 1000000]的范围内。
 * 操作的总数目在[1, 10000]范围内。
 * 不要使用内建的哈希库。
 */
public class Lc706 {

    class MyHashMap {

        private int N;
        private int M;
        private int[] keys;
        private int[] vals;
        private int zeroVal = -1;

        /** Initialize your data structure here. */
        public MyHashMap() {
            this(50);
        }

        private MyHashMap(int cap) {
            M = cap;
            keys = new int[M];
            vals = new int[M];
        }

        /** value will always be non-negative. */
        public void put(int key, int value) {
            if (key == 0) {
                zeroVal = value;
            }
            if (N >= M / 2) {
                resize(M * 2);
            }

            int i;
            for (i = hash(key); keys[i] != 0; i = (i + 1) % M) {
                if (keys[i] == key) {
                    vals[i] = value;
                    return;
                }
            }
            keys[i] = key;
            vals[i] = value;
            N++;
        }

        /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
        public int get(int key) {
            if (key == 0 && zeroVal != -1) {
                return zeroVal;
            }
            for (int i = hash(key); keys[i] != 0; i = (i + 1) % M) {
                if (keys[i] == key) {
                    return vals[i];
                }
            }

            return -1;
        }

        /** Removes the mapping of the specified value key if this map contains a mapping for the key */
        public void remove(int key) {
            if (key == 0) {
                zeroVal = -1;
            }
            if (get(key) == -1) return;

            // find position i of key
            int i = hash(key);
            while (key != keys[i]) {
                i = (i + 1) % M;
            }

            // delete key and associated value
            keys[i] = 0;
            vals[i] = 0;

            // rehash all keys in same cluster
            i = (i + 1) % M;
            while (keys[i] != 0) {
                // delete keys[i] an vals[i] and reinsert
                int keyToRehash = keys[i];
                int valToRehash = vals[i];
                keys[i] = 0;
                vals[i] = 0;
                N--;
                put(keyToRehash, valToRehash);
                i = (i + 1) % M;
            }

            N--;
        }

        private int hash(int key) {
            return (key & 0x7fffffff) % M;
        }

        private void resize(int cap) {
            MyHashMap temp = new MyHashMap(cap);

            for (int i = 0; i < M; i++) {
                if (keys[i] != 0) {
                    temp.put(keys[i], vals[i]);
                }
            }

            keys = temp.keys;
            vals = temp.vals;
            M = temp.M;
        }
    }
}
