package com.diwayou.acm.leetcode.lc500;

import java.util.List;

/**
 * https://leetcode-cn.com/problems/maximum-depth-of-n-ary-tree/
 * <p>
 * 给定一个 N 叉树，找到其最大深度。
 * 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。
 * <p>
 * 例如，给定一个3叉树:
 * 我们应返回其最大深度，3。
 * <p>
 * 说明:
 * 树的深度不会超过1000。
 * 树的节点总不会超过5000。
 */
public class Lc559 {

    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int m = 0;
        for (Node n : root.children) {
            m = Math.max(m, maxDepth(n));
        }

        return 1 + m;
    }

    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    ;
}
