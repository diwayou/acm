package com.diwayou.acm.leetcode.lc900;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/univalued-binary-tree/
 * <p>
 * 如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。
 * 只有给定的树是单值二叉树时，才返回 true；否则返回 false。
 * <p>
 * 提示：
 * 给定树的节点数范围是 [1, 100]。
 * 每个节点的值都是整数，范围为 [0, 99] 。
 */
public class Lc965 {

    private int v = -1;

    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        if (v == -1) {
            v = root.val;
        }

        if (root.val != v) {
            return false;
        }

        return isUnivalTree(root.left) && isUnivalTree(root.right);
    }
}
