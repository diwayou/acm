package com.diwayou.acm.leetcode.lc1100;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/binary-tree-coloring-game/
 */
public class Lc1145 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        System.out.println(new Lc1145().btreeGameWinningMove(root, 3, 2));
    }

    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        if (n <= 1) {
            return false;
        }

        TreeNode[] nodes = new TreeNode[n + 1];
        int[] cnt = new int[n + 1];

        int total = dfs(root, nodes, cnt);

        TreeNode xn = nodes[x];
        int xc = cnt[x];
        int left = 0, right = 0, p = 0;
        if (xn.left != null) {
            left = cnt[xn.left.val];
        }
        if (xn.right != null) {
            right = cnt[xn.right.val];
        }

        return ((total - xc) > xc) || ((total - left) < left) || ((total - right) < right);
    }

    private int dfs(TreeNode root, TreeNode[] nodes, int[] cnt) {
        nodes[root.val] = root;

        int l = 0, r = 0;
        if (root.left != null) {
            l = dfs(root.left, nodes, cnt);
        }
        if (root.right != null) {
            r = dfs(root.right, nodes, cnt);
        }

        cnt[root.val] = l + r + 1;

        return cnt[root.val];
    }
}
