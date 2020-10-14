package com.diwayou.acm.leetcode.lc500;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/diameter-of-binary-tree/
 *
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
 * 示例 :
 * 给定二叉树
 *
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 * 返回3, 它的长度是路径 [4,2,1,3] 或者[5,2,1,3]。
 *
 * 注意：两结点之间的路径长度是以它们之间边的数目表示。
 */
public class Lc543 {

    private int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        diameterOfBinaryTreeHelper(root);

        return max;
    }

    public int diameterOfBinaryTreeHelper(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int l = diameterOfBinaryTreeHelper(root.left);
        int r = diameterOfBinaryTreeHelper(root.right);

        if (l + r > max) {
            max = l + r;
        }

        return Math.max(l, r) + 1;
    }
}
