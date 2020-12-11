package com.diwayou.acm.leetcode.lc600;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/second-minimum-node-in-a-binary-tree/
 * <p>
 * 给定一个非空特殊的二叉树，每个节点都是正数，并且每个节点的子节点数量只能为2或0。如果一个节点有两个子节点的话，那么这个节点的值不大于它的子节点的值。
 * 给出这样的一个二叉树，你需要输出所有节点中的第二小的值。如果第二小的值不存在的话，输出 -1 。
 * <p>
 * 示例 1:
 * 输入:
 * 2
 * / \
 * 2   5
 * / \
 * 5   7
 * 输出: 5
 * 说明: 最小的值是 2 ，第二小的值是 5 。
 * <p>
 * 示例 2:
 * 输入:
 * 2
 * / \
 * 2   2
 * <p>
 * 输出: -1
 * 说明: 最小的值是 2, 但是不存在第二小的值。
 */
public class Lc671 {

    private int min;

    private long secondMin = Long.MAX_VALUE;

    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) {
            return -1;
        }

        min = root.val;

        findSecondMinimumValueHelper(root);

        return secondMin == Long.MAX_VALUE ? -1 : (int) secondMin;
    }

    private void findSecondMinimumValueHelper(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.val > min && root.val < secondMin) {
            secondMin = root.val;
        }

        findSecondMinimumValueHelper(root.left);
        findSecondMinimumValueHelper(root.right);
    }
}
