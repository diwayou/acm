package com.diwayou.acm.leetcode.lc400;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/all-oone-data-structure/
 *
 * 实现一个数据结构支持以下操作：
 *
 * Inc(key) - 插入一个新的值为 1 的 key。或者使一个存在的 key 增加一，保证 key 不为空字符串。
 * Dec(key) - 如果这个 key 的值是 1，那么把他从数据结构中移除掉。否者使一个存在的 key 值减一。如果这个 key 不存在，这个函数不做任何事情。key 保证不为空字符串。
 * GetMaxKey() - 返回 key 中值最大的任意一个。如果没有元素存在，返回一个空字符串""。
 * GetMinKey() - 返回 key 中值最小的任意一个。如果没有元素存在，返回一个空字符串""。
 *
 * 挑战：以 O(1) 的时间复杂度实现所有操作。
 */
public class Lc432 {

    private static class AllOne {

        private static class Node {

            private Set<String> keys = new HashSet<>();

            private int v;

            private Node prev, next;
        }

        private Map<String, Node> map;

        private Node head, tail;

        /** Initialize your data structure here. */
        public AllOne() {
            this.map = new HashMap<>();
            this.head = new Node();
            this.tail = new Node();
            this.head.next = tail;
            this.tail.prev = head;
        }

        /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
        public void inc(String key) {
            Node n = map.get(key);
            if (n == null) {
                if (head.next.v == 1) {
                    head.next.keys.add(key);
                    map.put(key, head.next);
                } else {
                    n = new Node();
                    n.v++;
                    n.keys.add(key);

                    n.next = head.next;
                    n.prev = head;
                    head.next.prev = n;
                    head.next = n;

                    map.put(key, n);
                }
            } else {
                if (n.next.v == n.v + 1) {
                    n.next.keys.add(key);

                    map.put(key, n.next);
                } else {
                    Node t = new Node();
                    t.v = n.v + 1;
                    t.keys.add(key);

                    t.prev = n;
                    t.next = n.next;
                    n.next.prev = t;
                    n.next = t;

                    map.put(key, t);
                }

                n.keys.remove(key);
                if (n.keys.isEmpty()) {
                    remove(n);
                }
            }
        }

        /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
        public void dec(String key) {
            Node n = map.get(key);
            if (n == null) {
                return;
            }

            if (n.v == 1) {
                map.remove(key);
            } else {
                if (n.prev.v == n.v - 1) {
                    n.prev.keys.add(key);

                    map.put(key, n.prev);
                } else {
                    Node t = new Node();
                    t.v = n.v - 1;
                    t.keys.add(key);

                    t.prev = n.prev;
                    t.next = n;
                    n.prev.next = t;
                    n.prev = t;

                    map.put(key, t);
                }
            }

            n.keys.remove(key);
            if (n.keys.isEmpty()) {
                remove(n);
            }
        }

        private void remove(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }

        /** Returns one of the keys with maximal value. */
        public String getMaxKey() {
            if (map.isEmpty()) {
                return "";
            }

            return tail.prev.keys.iterator().next();
        }

        /** Returns one of the keys with Minimal value. */
        public String getMinKey() {
            if (map.isEmpty()) {
                return "";
            }

            return head.next.keys.iterator().next();
        }
    }
}
