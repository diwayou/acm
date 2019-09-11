package com.diwayou.team;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 打家劫舍 https://leetcode-cn.com/problems/house-robber-iii/
 */
public class HouseRobberIII1 {

    public static void main(String[] args) {
        Integer[] input = {3, 4, 5, 1, 3, null, 1};
        TreeNode root = buildTree(input);

        System.out.println(new HouseRobberIII1().rob(root));
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

    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int[] res = doRob(root);

        return Math.max(res[0], res[1]);
    }

    private int[] doRob(TreeNode root) {
        if (root == null) {
            return new int[2];
        }

        int[] left = doRob(root.left);
        int[] right = doRob(root.right);

        int[] res = new int[2];
        res[0] = root.val + left[1] + right[1];
        res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return res;
    }
}
