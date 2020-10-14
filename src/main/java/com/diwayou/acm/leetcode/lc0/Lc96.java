package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/unique-binary-search-trees/
 *
 * 给定一个整数 n，求以1 ...n为节点组成的二叉搜索树有多少种？
 *
 * 示例:
 * 输入: 3
 * 输出: 5
 * 解释:
 * 给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 */
public class Lc96 {

    public static void main(String[] args) {
        System.out.println(new Lc96().numTrees(10));
    }

    public int numTrees(int n) {
        long re = 1;
        for (int i = 0; i < n; ++i) {
            re = re * 2 * (2 * i + 1) / (i + 2);
        }

        return (int) re;
    }
}
