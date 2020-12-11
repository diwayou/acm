package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/implement-trie-prefix-tree/
 * <p>
 * 实现一个 Trie (前缀树)，包含insert,search, 和startsWith这三个操作。
 * <p>
 * 示例:
 * Trie trie = new Trie();
 * <p>
 * trie.insert("apple");
 * trie.search("apple");   // 返回 true
 * trie.search("app");     // 返回 false
 * trie.startsWith("app"); // 返回 true
 * trie.insert("app");
 * trie.search("app");     // 返回 true
 * <p>
 * 说明:
 * 你可以假设所有的输入都是由小写字母a-z构成的。
 * 保证所有输入均为非空字符串。
 */
public class Lc208 {

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("apple");
        System.out.println(trie.search("apple"));   // 返回 true
        System.out.println(trie.search("app"));     // 返回 false
        System.out.println(trie.startsWith("app")); // 返回 true
        trie.insert("app");
        System.out.println(trie.search("app"));     // 返回 true
    }

    private static class Trie {

        private static class Node {
            public boolean has;
            public Node[] next = new Node[26];
        }

        private Node root;

        /**
         * Initialize your data structure here.
         */
        public Trie() {
            this.root = new Node();
        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            Node n = root;
            int idx;
            for (char ch : word.toCharArray()) {
                idx = ch - 'a';
                if (n.next[idx] == null) {
                    n.next[idx] = new Node();
                }

                n = n.next[idx];
            }

            n.has = true;
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            Node n = root;
            int idx;
            for (char ch : word.toCharArray()) {
                idx = ch - 'a';
                if (n.next[idx] == null) {
                    return false;
                }

                n = n.next[idx];
            }

            return n.has;
        }

        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            Node n = root;
            int idx;
            for (char ch : prefix.toCharArray()) {
                idx = ch - 'a';
                if (n.next[idx] == null) {
                    return false;
                }

                n = n.next[idx];
            }

            return true;
        }
    }
}
