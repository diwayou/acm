package com.diwayou.team;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 打家劫舍 https://leetcode-cn.com/problems/house-robber-iii/
 */
public class HouseRobberIII {

    public static void main(String[] args) {
        Integer[] input = {3, 4, 5, 1, 3, null, 1};
        TreeNode root = buildTree(input);

        System.out.println(new HouseRobberIII().rob(root));
    }

    private static TreeNode buildTree(Integer[] input) {
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(input[0]);
        queue.offer(root);
        for (int i = 1; i < input.length; i += 2) {
            TreeNode parent = queue.poll();
            if (parent == null) {
                break;
            }

            TreeNode left = null, right = null;
            if (input[i] != null) {
                left = new TreeNode(input[i]);
            }
            if (input[i + 1] != null) {
                right = new TreeNode(input[i + 1]);
            }

            parent.left = left;
            parent.right = right;

            queue.offer(left);
            queue.offer(right);
        }

        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private Map<TreeNode, Integer> dpUse = new HashMap<>();
    private Map<TreeNode, Integer> dpNotUse = new HashMap<>();

    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(doRob(root, false), doRob(root, true));
    }

    private int doRob(TreeNode root, boolean use) {
        if (root == null) {
            return 0;
        }

        if (use && dpUse.containsKey(root)) {
            return dpUse.get(root);
        } else if (!use && dpNotUse.containsKey(root)) {
            return dpNotUse.get(root);
        }

        int ret;
        if (use) {
            ret = root.val + doRob(root.left, false) + doRob(root.right, false);
        } else {
            ret = Math.max(doRob(root.left, true), doRob(root.left, false)) +
                    Math.max(doRob(root.right, true), doRob(root.right, false));
        }

        if (use) {
            dpUse.put(root, ret);
        } else {
            dpNotUse.put(root, ret);
        }

        return ret;
    }
}
