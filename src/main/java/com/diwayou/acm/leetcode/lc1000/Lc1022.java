package com.diwayou.acm.leetcode.lc1000;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/sum-of-root-to-leaf-binary-numbers/
 * <p>
 * 给出一棵二叉树，其上每个结点的值都是0或1。每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。例如，如果路径为0 -> 1 -> 1 -> 0 -> 1，那么它表示二进制数01101，也就是13。
 * 对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。
 * 以10^9 + 7为模，返回这些数字之和。
 * <p>
 * 示例：
 * 输入：[1,0,1,0,1,0,1]
 * 输出：22
 * 解释：(100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
 * <p>
 * 提示：
 * 树中的结点数介于 1 和 1000 之间。
 * node.val 为0 或1。
 */
public class Lc1022 {

    private static final int mod = 1000000007;

    private int re = 0;

    public int sumRootToLeaf(TreeNode root) {
        if (root == null) {
            return 0;
        }

        sumRootToLeafHelper(root, 0);

        return re;
    }

    private void sumRootToLeafHelper(TreeNode root, int cur) {
        if (root.left == null && root.right == null) {
            re += (cur << 1) | root.val;
            re %= mod;
        }

        cur = (cur << 1) | root.val;
        cur %= mod;
        if (root.left != null) {
            sumRootToLeafHelper(root.left, cur);
        }
        if (root.right != null) {
            sumRootToLeafHelper(root.right, cur);
        }
    }
}
