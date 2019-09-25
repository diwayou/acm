package com.diwayou.acm.leetcode.lc800;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/all-nodes-distance-k-in-binary-tree/
 *
 * 给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 K 。
 * 返回到目标结点 target 距离为 K 的所有结点的值的列表。 答案可以以任何顺序返回。
 */
public class Lc863 {

    private TreeNode[] parents = new TreeNode[501];
    private boolean[] mark = new boolean[501];

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> result = new ArrayList<>();

        initParents(root, null);
        distanceKHelper(target, K, 0, result);

        return result;
    }

    private void initParents(TreeNode root, TreeNode parent) {
        parents[root.val] = parent;

        if (root.left != null) {
            initParents(root.left, root);
        }
        if (root.right != null) {
            initParents(root.right, root);
        }
    }

    private void distanceKHelper(TreeNode root, int k, int distance, List<Integer> result) {
        if (mark[root.val]) {
            return;
        }

        if (distance == k) {
            result.add(root.val);
        }
        mark[root.val] = true;

        if (root.left != null) {
            distanceKHelper(root.left, k, distance + 1, result);
        }
        if (root.right != null) {
            distanceKHelper(root.right, k, distance + 1, result);
        }
        if (parents[root.val] != null) {
            distanceKHelper(parents[root.val], k, distance + 1, result);
        }
    }
}
