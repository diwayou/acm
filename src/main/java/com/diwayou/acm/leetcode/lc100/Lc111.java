package com.diwayou.acm.leetcode.lc100;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
 */
public class Lc111 {

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return minDepthHelper(root, 1);
    }

    private int minDepthHelper(TreeNode root, int d) {
        if (root.left == null && root.right == null) {
            return d;
        }

        int l = Integer.MAX_VALUE, r = Integer.MAX_VALUE;
        if (root.left != null) {
            l = minDepthHelper(root.left, d + 1);
        }
        if (root.right != null) {
            r = minDepthHelper(root.right, d + 1);
        }

        return Math.min(l, r);
    }
}
