package com.diwayou.acm.leetcode.lc100;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
 *
 * 给定一个二叉树，返回它的 前序 遍历。
 *
 *  示例:
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,2,3]
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
public class Lc144 {

    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<Integer> re = new ArrayList<>();
        preorderTraversalHelper(root, re);

        return re;
    }

    private void preorderTraversalHelper(TreeNode root, List<Integer> re) {
        if (root == null) {
            return;
        }

        re.add(root.val);

        preorderTraversalHelper(root.left, re);
        preorderTraversalHelper(root.right, re);
    }
}
