package com.diwayou.acm.leetcode.lc600;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst/
 *
 * 给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。
 *
 * 案例 1:
 * 输入:
 *     5
 *    / \
 *   3   6
 *  / \   \
 * 2   4   7
 * Target = 9
 * 输出: True
 *
 * 案例 2:
 * 输入:
 *     5
 *    / \
 *   3   6
 *  / \   \
 * 2   4   7
 * Target = 28
 * 输出: False
 */
public class Lc653 {

    private Set<Integer> s = new HashSet<>();

    public boolean findTarget1(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        if (s.contains(k - root.val)) {
            return true;
        }
        s.add(root.val);

        return findTarget(root.left, k) || findTarget(root.right, k);
    }

    public boolean findTarget(TreeNode root, int k) {
        return findTarget(root, root, k);
    }

    private boolean findTarget(TreeNode cur, TreeNode root, int k) {
        if (cur == null) {
            return false;
        }

        if (help(cur, root, k - cur.val)) {
            return true;
        }

        return findTarget(cur.right, root, k) || findTarget(cur.left, root, k);
    }

    private boolean help(TreeNode cur, TreeNode root, int target) {
        while (root != null) {
            if (target > root.val) {
                root = root.right;
            } else if (target < root.val) {
                root = root.left;
            } else {
                return root != cur;
            }
        }

        return false;
    }
}
