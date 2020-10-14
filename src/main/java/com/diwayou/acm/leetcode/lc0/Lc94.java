package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 *
 * 给定一个二叉树，返回它的中序遍历。
 * 示例:
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,3,2]
 *
 * 进阶:递归算法很简单，你可以通过迭代算法完成吗？
 */
public class Lc94 {

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        n1.right = n2;
        n2.left = n3;

        new Lc94().inorderTraversal(n1);
    }

    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> re = new ArrayList<>();

        inorderTraversalHelper(root, re);

        return re;
    }

    private void inorderTraversalHelper(TreeNode root, List<Integer> re) {
        if (root == null) {
            return;
        }

        inorderTraversalHelper(root.left, re);
        re.add(root.val);
        inorderTraversalHelper(root.right, re);
    }

    public List<Integer> inorderTraversal2(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<Integer> re = new ArrayList<>();

        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.left;
            }

            cur = stack.removeLast();
            re.add(cur.val);

            cur = cur.right;
        }

        return re;
    }

    // 线索二叉树
    public List < Integer > inorderTraversal(TreeNode root) {
        List < Integer > res = new ArrayList < > ();
        TreeNode curr = root;
        TreeNode pre;
        while (curr != null) {
            if (curr.left == null) {
                res.add(curr.val);
                curr = curr.right; // move to next right node
            } else { // has a left subtree
                pre = curr.left;
                while (pre.right != null) { // find rightmost
                    pre = pre.right;
                }
                pre.right = curr; // put cur after the pre node
                TreeNode temp = curr; // store cur node
                curr = curr.left; // move cur to the top of the new tree
                temp.left = null; // original cur left be null, avoid infinite loops
            }
        }
        return res;
    }
}
