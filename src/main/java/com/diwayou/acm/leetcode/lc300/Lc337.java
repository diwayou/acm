package com.diwayou.acm.leetcode.lc300;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/house-robber-iii/
 *
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有
 * 且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚
 * 上被打劫，房屋将自动报警。
 * 计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 *
 * 示例 1:
 * 输入: [3,2,3,null,3,null,1]
 *
 *      3
 *     / \
 *    2   3
 *     \   \
 *      3   1
 *
 * 输出: 7
 * 解释: 小偷一晚能够盗取的最高金额 = 3 + 3 + 1 = 7.
 *
 * 示例 2:
 * 输入: [3,4,5,1,3,null,1]
 *
 *      3
 *     / \
 *    4   5
 *   / \   \
 *  1   3   1
 *
 * 输出: 9
 * 解释: 小偷一晚能够盗取的最高金额 = 4 + 5 = 9.
 */
public class Lc337 {

    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] re = robHelper(root);

        return Math.max(re[0], re[1]);
    }

    private int[] robHelper(TreeNode root) {
        int[] l = new int[]{0, 0}, r = new int[]{0, 0};
        if (root.left != null) {
            l = robHelper(root.left);
        }
        if (root.right != null) {
            r = robHelper(root.right);
        }

        int t0 = l[0] + r[0], t1 = l[1] + r[1];

        return new int[]{Math.max(root.val + t1, t0), Math.max(t0, t1)};
    }
}
