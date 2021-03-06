package com.diwayou.acm.leetcode.lc600;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/longest-univalue-path/
 * <p>
 * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
 * 注意：两个节点之间的路径长度由它们之间的边数表示。
 * <p>
 * 示例 1:
 * 输入:
 * 5
 * / \
 * 4   5
 * / \   \
 * 1   1   5
 * 输出:
 * 2
 * <p>
 * 示例 2:
 * 输入:
 * 1
 * / \
 * 4   5
 * / \   \
 * 4   4   5
 * 输出:
 * 2
 * 注意: 给定的二叉树不超过10000个结点。树的高度不超过1000。
 */
public class Lc687 {

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int max = longestUnivaluePathHelper(root.left, 0, root.val) + longestUnivaluePathHelper(root.right, 0, root.val);

        return Math.max(max, Math.max(longestUnivaluePath(root.left), longestUnivaluePath(root.right)));
    }

    private int longestUnivaluePathHelper(TreeNode root, int l, int pv) {
        if (root == null || root.val != pv) {
            return l;
        }

        return Math.max(longestUnivaluePathHelper(root.left, l + 1, pv), longestUnivaluePathHelper(root.right, l + 1, pv));
    }

    private int res;

    public int longestUnivaluePath1(TreeNode root) {
        res = 0;
        longestTreePath(root);
        return res;
    }

    private int longestTreePath(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftLen = longestTreePath(node.left);
        int rightLen = longestTreePath(node.right);

        int curLeft = 0;
        int curRight = 0;
        if (node.left != null && node.left.val == node.val) {
            curLeft = leftLen + 1;
        }
        if (node.right != null && node.right.val == node.val) {
            curRight = rightLen + 1;
        }

        res = Math.max(res, curLeft + curRight);

        return Math.max(curLeft, curRight);
    }
}
