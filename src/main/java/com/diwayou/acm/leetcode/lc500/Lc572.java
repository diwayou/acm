package com.diwayou.acm.leetcode.lc500;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/subtree-of-another-tree/
 *
 * 给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。
 *
 * 示例 1:
 * 给定的树 s:
 *
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 * 给定的树 t：
 *    4
 *   / \
 *  1   2
 * 返回 true，因为 t 与 s 的一个子树拥有相同的结构和节点值。
 *
 * 示例 2:
 * 给定的树 s：
 *      3
 *     / \
 *    4   5
 *   / \
 *  1   2
 *     /
 *    0
 * 给定的树 t：
 *    4
 *   / \
 *  1   2
 * 返回 false。
 */
public class Lc572 {

    private boolean isEqual(TreeNode l, TreeNode r) {
        if (l == null && r == null) {
            return true;
        }
        if (l == null || r == null) {
            return false;
        }

        if (l.val == r.val) {
            return isEqual(l.left, r.left) && isEqual(l.right, r.right);
        }

        return false;
    }

    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }

        if (s.val == t.val) {
            return isEqual(s, t) || isSubtree(s.left, t) || isSubtree(s.right, t);
        }

        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    public boolean isSubtree1(TreeNode s, TreeNode t) {
        return findSubtree(s, t, t);
    }

    public boolean findSubtree(TreeNode s, TreeNode t, TreeNode orit) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }

        if (s.val == t.val) {
            return (findSubtree(s.left, t.left, orit) && findSubtree(s.right, t.right, orit)) ||
                    (findSubtree(s.left, t, orit) || findSubtree(s.right, t, orit));
        } else {
            return findSubtree(s.left, orit, orit) || findSubtree(s.right, orit, orit);
        }
    }
}
