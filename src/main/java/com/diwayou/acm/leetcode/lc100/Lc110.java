package com.diwayou.acm.leetcode.lc100;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/balanced-binary-tree/
 */
public class Lc110 {

    private boolean result = true;

    public boolean isBalanced(TreeNode root) {
        isBalancedHelper(root);

        return result;
    }

    private int isBalancedHelper(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int l = isBalancedHelper(root.left);
        int r = isBalancedHelper(root.right);

        if (l < r - 1 || r < l - 1) {
            result = false;
        }

        return 1 + Math.max(l, r);
    }
}
