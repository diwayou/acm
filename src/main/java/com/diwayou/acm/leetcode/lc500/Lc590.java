package com.diwayou.acm.leetcode.lc500;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/n-ary-tree-postorder-traversal/
 * <p>
 * 给定一个 N 叉树，返回其节点值的后序遍历。
 */
public class Lc590 {

    private List<Integer> re = new ArrayList<>();

    public List<Integer> postorder(Node root) {
        if (root == null) {
            return re;
        }

        for (Node n : root.children) {
            postorder(n);
        }

        re.add(root.val);

        return re;
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
