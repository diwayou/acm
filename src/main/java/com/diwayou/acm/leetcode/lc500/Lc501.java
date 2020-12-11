package com.diwayou.acm.leetcode.lc500;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/find-mode-in-binary-search-tree/
 * <p>
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 * 假定 BST 有如下定义：
 * 结点左子树中所含结点的值小于等于当前结点的值
 * 结点右子树中所含结点的值大于等于当前结点的值
 * 左子树和右子树都是二叉搜索树
 * 例如：
 * 给定 BST [1,null,2,2],
 * <p>
 * 1
 * \
 * 2
 * /
 * 2
 * 返回[2].
 * 提示：如果众数超过1个，不需考虑输出顺序
 * <p>
 * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
 */
public class Lc501 {

    private int maxTimes = 0;
    private int thisTimes = 0;
    private List<Integer> res = new ArrayList<>();
    private TreeNode pre = null;

    public int[] findMode(TreeNode root) {
        inOrder(root);

        int length = res.size();
        int[] rr = new int[length];
        for (int i = 0; i < length; i++) {
            rr[i] = res.get(i);
        }

        return rr;
    }

    public void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        inOrder(root.left);

        if (pre != null && pre.val == root.val) {
            thisTimes++;
        } else {
            thisTimes = 1;
        }

        if (thisTimes == maxTimes) {
            res.add(root.val);
        } else if (thisTimes > maxTimes) {
            maxTimes = thisTimes;
            res.clear();
            res.add(root.val);
        }

        pre = root;

        inOrder(root.right);
    }
}
