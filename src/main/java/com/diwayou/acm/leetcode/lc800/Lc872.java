package com.diwayou.acm.leetcode.lc800;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/leaf-similar-trees/
 */
public class Lc872 {

    private int n;

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        TreeNode[] leafs = new TreeNode[100];

        n = 0;
        buildLeafs(root1, leafs);

        n = 0;
        return checkLeafs(root2, leafs);
    }

    private boolean checkLeafs(TreeNode root2, TreeNode[] leafs) {
        if (root2 == null) {
            return true;
        }

        if (root2.left == null && root2.right == null) {
            if (leafs[n] == null || leafs[n++].val != root2.val) {
                return false;
            }
        }

        return checkLeafs(root2.left, leafs) && checkLeafs(root2.right, leafs);
    }

    private void buildLeafs(TreeNode root1, TreeNode[] leafs) {
        if (root1 == null) {
            return;
        }

        if (root1.left == null && root1.right == null) {
            leafs[n++] = root1;
        }

        buildLeafs(root1.left, leafs);
        buildLeafs(root1.right, leafs);
    }
}
