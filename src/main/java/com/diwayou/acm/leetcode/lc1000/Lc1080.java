package com.diwayou.acm.leetcode.lc1000;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/insufficient-nodes-in-root-to-leaf-paths/
 */
public class Lc1080 {

    public TreeNode sufficientSubset(TreeNode root, int limit) {
        if (root == null) {
            return null;
        }

        return sufficientSubsetHelper(root, limit, 0);
    }

    private TreeNode sufficientSubsetHelper(TreeNode root, int limit, int cur) {
        boolean isLeaf = (root.left == null && root.right == null);

        if (root.left != null) {
            root.left = sufficientSubsetHelper(root.left, limit, cur + root.val);
        }
        if (root.right != null) {
            root.right = sufficientSubsetHelper(root.right, limit, cur + root.val);
        }

        if (root.left == null && root.right == null) {
            // 非叶子结点如果子节点都被删除了，也要删除掉
            if (!isLeaf) {
                return null;
            } else if (cur + root.val < limit){
                return null;
            }
        }

        return root;
    }
}
