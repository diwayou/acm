package com.diwayou.acm.leetcode.lc100;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 *
 * 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 * 例如:
 * 给定二叉树: [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回其层次遍历结果：
 *
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 */
public class Lc102 {

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        Deque<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);

        List<List<Integer>> re = new ArrayList<>();
        while (!queue.isEmpty()) {
            int cnt = queue.size();

            List<Integer> tl = new ArrayList<>(cnt);
            for (int i = 0; i < cnt; i++) {
                TreeNode tn = queue.removeFirst();
                tl.add(tn.val);

                if (tn.left != null) {
                    queue.addLast(tn.left);
                }
                if (tn.right != null) {
                    queue.addLast(tn.right);
                }
            }

            re.add(tl);
        }

        return re;
    }
}
