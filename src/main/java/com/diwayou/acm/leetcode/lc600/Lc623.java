package com.diwayou.acm.leetcode.lc600;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/add-one-row-to-tree/
 * <p>
 * 给定一个二叉树，根节点为第1层，深度为 1。在其第d层追加一行值为v的节点。
 * 添加规则：给定一个深度值 d （正整数），针对深度为 d-1 层的每一非空节点 N，为 N 创建两个值为v的左子树和右子树。
 * 将N 原先的左子树，连接为新节点v 的左子树；将N 原先的右子树，连接为新节点v 的右子树。
 * 如果 d 的值为 1，深度 d - 1 不存在，则创建一个新的根节点 v，原先的整棵树将作为 v 的左子树。
 */
public class Lc623 {

    public TreeNode addOneRow(TreeNode root, int v, int d) {
        if (d == 1) {
            TreeNode node = new TreeNode(v);
            node.left = root;

            return node;
        }

        int cur = 1;
        addOneRowHelper(root, v, d, cur);

        return root;
    }

    private void addOneRowHelper(TreeNode root, int v, int d, int cur) {
        if (cur == d - 1) {
            TreeNode left = new TreeNode(v);
            TreeNode right = new TreeNode(v);
            if (root.left != null) {
                left.left = root.left;
            }
            root.left = left;
            if (root.right != null) {
                right.right = root.right;
            }
            root.right = right;
        } else if (cur < d - 1) {
            if (root.left != null) {
                addOneRowHelper(root.left, v, d, cur + 1);
            }
            if (root.right != null) {
                addOneRowHelper(root.right, v, d, cur + 1);
            }
        }
    }
}
