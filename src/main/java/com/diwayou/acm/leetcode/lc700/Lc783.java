package com.diwayou.acm.leetcode.lc700;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/minimum-distance-between-bst-nodes/
 * <p>
 * 给定一个二叉搜索树的根结点root, 返回树中任意两节点的差的最小值。
 * <p>
 * 示例：
 * 输入: root = [4,2,6,1,3,null,null]
 * 输出: 1
 * 解释:
 * 注意，root是树结点对象(TreeNode object)，而不是数组。
 * <p>
 * 给定的树 [4,2,6,1,3,null,null] 可表示为下图:
 * <p>
 * 4
 * /   \
 * 2      6
 * / \
 * 1   3
 * <p>
 * 最小的差值是 1, 它是节点1和节点2的差值, 也是节点3和节点2的差值。
 * 注意：
 * 二叉树的大小范围在 2 到100。
 * 二叉树总是有效的，每个节点的值都是整数，且不重复。
 */
public class Lc783 {

    private TreeNode pre;
    private int min = Integer.MAX_VALUE;

    public int minDiffInBST(TreeNode root) {
        inOrder(root);

        return min;
    }

    void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        inOrder(root.left);

        if (pre != null) {
            min = Math.min(root.val - pre.val, min);
        }
        pre = root;

        inOrder(root.right);
    }
}
