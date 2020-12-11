package com.diwayou.acm.leetcode.lc600;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/find-duplicate-subtrees/
 * <p>
 * 给定一棵二叉树，返回所有重复的子树。对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
 * 两棵树重复是指它们具有相同的结构以及相同的结点值。
 * <p>
 * 示例 1：
 * <p>
 * 1
 * / \
 * 2   3
 * /   / \
 * 4   2   4
 * /
 * 4
 * 下面是两个重复的子树：
 * <p>
 * 2
 * /
 * 4
 * 和
 * <p>
 * 4
 * 因此，你需要以列表的形式返回上述重复子树的根结点。
 *
 * @author gaopeng
 * @date 2020/10/12
 */
public class Lc652 {

    int t;
    Map<String, Integer> trees;
    Map<Integer, Integer> count;
    List<TreeNode> ans;

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        t = 1;
        trees = new HashMap<>();
        count = new HashMap<>();
        ans = new ArrayList<>();
        lookup(root);
        return ans;
    }

    public int lookup(TreeNode node) {
        if (node == null) return 0;
        String serial = node.val + "," + lookup(node.left) + "," + lookup(node.right);
        int uid = trees.computeIfAbsent(serial, x -> t++);
        count.put(uid, count.getOrDefault(uid, 0) + 1);
        if (count.get(uid) == 2)
            ans.add(node);
        return uid;
    }

    Map<String, Integer> count1;
    List<TreeNode> ans1;

    public List<TreeNode> findDuplicateSubtrees1(TreeNode root) {
        count1 = new HashMap<>();
        ans1 = new ArrayList<>();
        collect(root);
        return ans1;
    }

    public String collect(TreeNode node) {
        if (node == null) return "#";
        String serial = node.val + "," + collect(node.left) + "," + collect(node.right);
        count1.put(serial, count1.getOrDefault(serial, 0) + 1);
        if (count1.get(serial) == 2)
            ans1.add(node);
        return serial;
    }

    List<TreeNode> result;
    Map<Integer, Integer> counts;

    public List<TreeNode> findDuplicateSubtrees2(TreeNode root) {
        result = new ArrayList<>();
        counts = new HashMap<>();
        dfs(root);
        return result;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 3524335;
        int id = ((dfs(node.left) ^ 3) * 3423443 + (dfs(node.right) ^ 13)) * 3423443 + node.val;
        int count = counts.getOrDefault(id, 0) + 1;
        if (count == 2) {
            result.add(node);
        }
        counts.put(id, count);
        return id;
    }
}
