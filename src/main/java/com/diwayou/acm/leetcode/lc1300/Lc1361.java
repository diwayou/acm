package com.diwayou.acm.leetcode.lc1300;

/**
 * https://leetcode-cn.com/problems/validate-binary-tree-nodes/
 *
 * 二叉树上有 n个节点，按从0到 n - 1编号，其中节点i的两个子节点分别是leftChild[i]和rightChild[i]。
 * 只有 所有 节点能够形成且 只 形成 一颗有效的二叉树时，返回true；否则返回 false。
 * 如果节点i没有左子节点，那么leftChild[i]就等于-1。右子节点也符合该规则。
 *
 * 注意：节点没有值，本问题中仅仅使用节点编号。
 *
 * 提示：
 * 1 <= n <= 10^4
 * leftChild.length == rightChild.length == n
 * -1 <= leftChild[i], rightChild[i] <= n - 1
 *
 * @author gaopeng
 * @date 2020/9/28
 */
public class Lc1361 {

    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        return true;
    }
}
