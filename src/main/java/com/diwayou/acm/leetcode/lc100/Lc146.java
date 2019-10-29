package com.diwayou.acm.leetcode.lc100;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/lru-cache/
 *
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 * 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。
 *
 * 进阶:
 * 你是否可以在 O(1) 时间复杂度内完成这两种操作？
 *
 * 示例:
 * LRUCache cache = new LRUCache( 2 );
 *
 * cache.put(1,1);
 * cache.put(2,2);
 * cache.get(1);       // 返回  1
 * cache.put(3,3);    // 该操作会使得密钥 2 作废
 * cache.get(2);       // 返回 -1 (未找到)
 * cache.put(4,4);    // 该操作会使得密钥 1 作废
 * cache.get(1);       // 返回 -1 (未找到)
 * cache.get(3);       // 返回  3
 * cache.get(4);       // 返回  4
 */
public class Lc146 {

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
        cache.put(4, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }

    private static class LRUCache {

        private Map<Integer, Node> cache;

        private Node head, tail;

        private int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>(capacity);
            this.head = new Node();
            this.tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            Node node = cache.get(key);
            if (node == null) {
                return -1;
            }

            node.prev.next = node.next;
            node.next.prev = node.prev;

            tail.prev.next = node;
            node.prev = tail.prev;
            tail.prev = node;
            node.next = tail;

            return node.v;
        }

        public void put(int key, int value) {
            Node node = cache.get(key);
            if (node != null) {
                node.v = value;

                node.prev.next = node.next;
                node.next.prev = node.prev;

                tail.prev.next = node;
                node.prev = tail.prev;
                tail.prev = node;
                node.next = tail;

                return;
            }

            if (cache.size() >= capacity) {
                cache.remove(head.next.k);
                head.next.next.prev = head;
                head.next = head.next.next;
            }

            node = new Node(key, value);
            tail.prev.next = node;
            node.prev = tail.prev;
            node.next = tail;
            tail.prev = node;

            cache.put(key, node);
        }

        private static class Node {
            public Node() {
            }

            public Node(int k, int v) {
                this.k = k;
                this.v = v;
            }

            int k;
            int v;
            Node next, prev;
        }
    }
}
