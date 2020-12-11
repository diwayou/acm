package com.diwayou.acm.leetcode.lc400;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/
 * <p>
 * 给定一个 N 叉树，返回其节点值的层序遍历。 (即从左到右，逐层遍历)。
 * <p>
 * 说明:
 * 树的深度不会超过 1000。
 * 树的节点总数不会超过 5000。
 */
public class Lc429 {

    List<List<Integer>> returnList = new ArrayList<>();

    public List<List<Integer>> levelOrder(Node root) {
        if (root == null) {
            return returnList;
        }
        helper(root, 0);

        return returnList;
    }

    public void helper(Node root, int index) {
        if (index == returnList.size()) {
            returnList.add(new ArrayList<>());
        }
        returnList.get(index).add(root.val);
        for (int i = 0; i < root.children.size(); i++) {
            helper(root.children.get(i), index + 1);
        }
    }

    public List<List<Integer>> levelOrder1(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<List<Integer>> re = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> l = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                Node n = queue.poll();
                l.add(n.val);

                queue.addAll(n.children);
            }

            re.add(l);
        }

        return re;
    }

    private static class Node {
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
