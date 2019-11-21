package com.diwayou.acm.leetcode.lc500;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal/
 *
 * 给定一个 N 叉树，返回其节点值的前序遍历。
 * 例如，给定一个 3叉树 :
 * 返回其前序遍历: [1,3,5,6,2,4]。
 *
 * 说明: 递归法很简单，你可以使用迭代法完成此题吗?
 */
public class Lc589 {

    private List<Integer> re = new ArrayList<>();

    public List<Integer> preorder(Node root) {
        if (root == null) {
            return re;
        }

        re.add(root.val);

        for (Node n : root.children) {
            preorder(n);
        }

        return re;
    }

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };
}
