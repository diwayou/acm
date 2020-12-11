package com.diwayou.acm.leetcode.lc600;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/trim-a-binary-search-tree/
 * <p>
 * 给定一个二叉搜索树，同时给定最小边界L和最大边界R。通过修剪二叉搜索树，使得所有节点的值在[L, R]中 (R>=L) 。
 * 你可能需要改变树的根节点，所以结果应当返回修剪好的二叉搜索树的新的根节点。
 * <p>
 * 示例 1:
 * 输入:
 * 1
 * / \
 * 0   2
 * <p>
 * L = 1
 * R = 2
 * 输出:
 * 1
 * \
 * 2
 * <p>
 * 示例 2:
 * 输入:
 * 3
 * / \
 * 0   4
 * \
 * 2
 * /
 * 1
 * L = 1
 * R = 3
 * 输出:
 * 3
 * /
 * 2
 * /
 * 1
 */
public class Lc669 {

    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) {
            return null;
        }

        if (root.val < L) {
            return trimBST(root.right, L, R);
        } else if (root.val > R) {
            return trimBST(root.left, L, R);
        } else {
            root.left = trimBST(root.left, L, R);
            root.right = trimBST(root.right, L, R);

            return root;
        }
    }
}
