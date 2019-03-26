package com.diwayou.acm.leetcode.tree;

// Given a binary tree, return all root-to-leaf paths.

// For example, given the following binary tree:

//    1
//  /   \
// 2     3
//  \
//   5
// All root-to-leaf paths are:

// ["1->2->5", "1->3"]

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
public class BinaryTreePaths {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<String>();

        if (root == null) {
            return result;
        }

        helper("", root, result);

        return result;
    }

    public static void helper(String current, TreeNode root, List<String> result) {
        if (root.left == null && root.right == null) {
            result.add(current + root.val);
        }

        if (root.left != null) {
            helper(current + root.val + "->", root.left, result);
        }

        if (root.right != null) {
            helper(current + root.val + "->", root.right, result);
        }
    }
}
