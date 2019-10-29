package com.diwayou.acm.leetcode.lc500;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/convert-bst-to-greater-tree/
 *
 * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
 *
 * 例如：
 * 输入: 二叉搜索树:
 *               5
 *             /   \
 *            2     13
 *
 * 输出: 转换为累加树:
 *              18
 *             /   \
 *           20     13
 */
public class Lc538 {

    private int add = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return root;
        }

        convertBST(root.right);

        root.val += add;
        add = root.val;

        convertBST(root.left);

        return root;
    }
}
