package com.diwayou.acm.leetcode.lc700;

/**
 * https://leetcode-cn.com/problems/design-hashset/
 * <p>
 * 不使用任何内建的哈希表库设计一个哈希集合
 * 具体地说，你的设计应该包含以下的功能
 * <p>
 * add(value)：向哈希集合中插入一个值。
 * contains(value) ：返回哈希集合中是否存在这个值。
 * remove(value)：将给定值从哈希集合中删除。如果哈希集合中没有这个值，什么也不做。
 * <p>
 * 示例:
 * MyHashSet hashSet = new MyHashSet();
 * hashSet.add(1);
 * hashSet.add(2);
 * hashSet.contains(1); // 返回 true
 * hashSet.contains(3); // 返回 false (未找到)
 * hashSet.add(2);
 * hashSet.contains(2); // 返回 true
 * hashSet.remove(2);
 * hashSet.contains(2); // 返回  false (已经被删除)
 * <p>
 * 注意：
 * 所有的值都在[1, 1000000]的范围内。
 * 操作的总数目在[1, 10000]范围内。
 * 不要使用内建的哈希集合库。
 */
public class Lc705 {

    private static class MyHashSet {

        private int M;
        private int N;
        private int[] keys;
        private boolean hasZero = false;

        /**
         * Initialize your data structure here.
         */
        public MyHashSet() {
            this(50);
        }

        public MyHashSet(int cap) {
            M = cap;
            keys = new int[M];
        }

        public void add(int key) {
            if (key == 0) {
                hasZero = true;
                return;
            }

            if (N >= M / 2) resize(2 * M);

            int i;
            for (i = hash(key); keys[i] != 0; i = (i + 1) % M) {
                if (keys[i] == key) {
                    return;
                }
            }
            keys[i] = key;
            N++;
        }

        public void remove(int key) {
            if (key == 0) {
                hasZero = false;
                return;
            }

            if (!contains(key)) return;

            // find position i of key
            int i = hash(key);
            while (key != keys[i]) {
                i = (i + 1) % M;
            }

            // delete key and associated value
            keys[i] = 0;

            // rehash all keys in same cluster
            i = (i + 1) % M;
            while (keys[i] != 0) {
                // delete keys[i] an vals[i] and reinsert
                int keyToRehash = keys[i];
                keys[i] = 0;
                N--;
                add(keyToRehash);
                i = (i + 1) % M;
            }

            N--;
        }

        /**
         * Returns true if this set contains the specified element
         */
        public boolean contains(int key) {
            if (key == 0) {
                return hasZero;
            }

            for (int i = hash(key); keys[i] != 0; i = (i + 1) % M) {
                if (keys[i] == key) {
                    return true;
                }
            }

            return false;
        }

        private int hash(int key) {
            return (key & 0x7fffffff) % M;
        }

        private void resize(int cap) {
            MyHashSet temp = new MyHashSet(cap);
            for (int i = 0; i < M; i++) {
                if (keys[i] != 0) {
                    temp.add(keys[i]);
                }
            }
            keys = temp.keys;
            M = temp.M;
        }
    }
}

