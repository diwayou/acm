package com.diwayou.acm.leetcode.lc200;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/binary-tree-paths/
 *
 * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
 *
 * 说明:叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 输入:
 *    1
 *  /   \
 * 2     3
 *  \
 *   5
 * 输出: ["1->2->5", "1->3"]
 *
 * 解释: 所有根节点到叶子节点的路径为: 1->2->5, 1->3
 */
public class Lc257 {

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> path = new ArrayList<>();
        getPath(root, "", path);

        return path;
    }

    public void getPath(TreeNode root, String current, List<String> path) {
        if (root == null) {
            return;
        }

        current += Integer.toString(root.val);
        if (root.left == null && root.right == null) {
            path.add(current);
        } else {
            // 添加当前节点
            current += "->";
            getPath(root.left, current, path);
            getPath(root.right, current, path);
        }
    }
}
