package com.diwayou.acm.leetcode.lc900;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/cousins-in-binary-tree/
 * <p>
 * 在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。
 * 如果二叉树的两个节点深度相同，但父节点不同，则它们是一对堂兄弟节点。
 * 我们给出了具有唯一值的二叉树的根节点 root，以及树中两个不同节点的值 x 和 y。
 * 只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true。否则，返回 false。
 * <p>
 * 示例 1：
 * 输入：root = [1,2,3,4], x = 4, y = 3
 * 输出：false
 * <p>
 * 示例 2：
 * 输入：root = [1,2,3,null,4,null,5], x = 5, y = 4
 * 输出：true
 * <p>
 * 示例 3：
 * 输入：root = [1,2,3,null,4], x = 2, y = 3
 * 输出：false
 * <p>
 * 提示：
 * 二叉树的节点数介于2 到100之间。
 * 每个节点的值都是唯一的、范围为1 到100的整数。
 */
public class Lc993 {

    private int xd;
    private int yd;
    private TreeNode xp;
    private TreeNode yp;
    private int x;
    private int y;

    public boolean isCousins(TreeNode root, int x, int y) {
        this.x = x;
        this.y = y;

        isCousinsHelper(root, 0, null);

        return xd == yd && xp != yp;
    }

    private void isCousinsHelper(TreeNode root, int d, TreeNode p) {
        if (root == null) {
            return;
        }

        if (root.val == x) {
            xd = d;
            xp = p;
        } else if (root.val == y) {
            yd = d;
            yp = p;
        }

        isCousinsHelper(root.left, d + 1, root);
        isCousinsHelper(root.right, d + 1, root);
    }
}
