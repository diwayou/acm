package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/unique-binary-search-trees-ii/
 *
 * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的二叉搜索树。
 *
 * 示例:
 * 输入: 3
 * 输出:
 * [
 *   [1,null,3,2],
 *   [3,2,null,1],
 *   [3,1,null,null,2],
 *   [2,1,3],
 *   [1,null,2,null,3]
 * ]
 * 解释:
 * 以上的输出对应以下 5 种不同结构的二叉搜索树：
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 */
public class Lc95 {

    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return Collections.emptyList();
        }

        return generateTreesHelper(1, n);
    }

    public List<TreeNode> generateTreesHelper(int start, int end) {
        List<TreeNode> re = new LinkedList<>();
        if (start > end) {
            re.add(null);
            return re;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> lt = generateTreesHelper(start, i - 1);
            List<TreeNode> rt = generateTreesHelper(i + 1, end);

            // connect left and right trees to the root i
            for (TreeNode l : lt) {
                for (TreeNode r : rt) {
                    TreeNode cur = new TreeNode(i);
                    cur.left = l;
                    cur.right = r;

                    re.add(cur);
                }
            }
        }

        return re;
    }
}
