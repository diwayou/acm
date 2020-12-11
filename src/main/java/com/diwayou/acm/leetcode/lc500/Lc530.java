package com.diwayou.acm.leetcode.lc500;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/minimum-absolute-difference-in-bst/
 * <p>
 * 给定一个所有节点为非负值的二叉搜索树，求树中任意两节点的差的绝对值的最小值。
 * <p>
 * 示例 :
 * 输入:
 * <p>
 * 1
 * \
 * 3
 * /
 * 2
 * <p>
 * 输出:
 * 1
 * 解释:
 * 最小绝对差为1，其中 2 和 1 的差的绝对值为 1（或者 2 和 3）。
 * 注意: 树中至少有2个节点。
 */
public class Lc530 {

    private TreeNode pre;

    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }

        int min = getMinimumDifference(root.left);

        if (pre != null) {
            min = Math.min(min, root.val - pre.val);
        }

        pre = root;

        return Math.min(min, getMinimumDifference(root.right));
    }
}
