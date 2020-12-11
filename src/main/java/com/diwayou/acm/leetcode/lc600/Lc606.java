package com.diwayou.acm.leetcode.lc600;

import com.diwayou.acm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/construct-string-from-binary-tree/
 * <p>
 * 你需要采用前序遍历的方式，将一个二叉树转换成一个由括号和整数组成的字符串。
 * 空节点则用一对空括号 "()" 表示。而且你需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。
 * <p>
 * 示例 1:
 * 输入: 二叉树: [1,2,3,4]
 * 1
 * /   \
 * 2     3
 * /
 * 4
 * 输出: "1(2(4))(3)"
 * 解释: 原本将是“1(2(4)())(3())”，
 * 在你省略所有不必要的空括号对之后，
 * 它将是“1(2(4))(3)”。
 * <p>
 * 示例 2:
 * 输入: 二叉树: [1,2,3,null,4]
 * 1
 * /   \
 * 2     3
 * \
 * 4
 * 输出: "1(2()(4))(3)"
 * 解释: 和第一个示例相似，
 * 除了我们不能省略第一个对括号来中断输入和输出之间的一对一映射关系。
 */
public class Lc606 {

    public String tree2str1(TreeNode t) {
        if (t == null) {
            return "";
        }

        if (t.left == null && t.right == null) {
            return String.valueOf(t.val);
        }

        return "" + t.val + "(" + tree2str(t.left) + ")" + (t.right == null ? "" : "(" + tree2str(t.right) + ")");
    }

    public String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        }

        if (t.left == null && t.right == null) {
            return String.valueOf(t.val);
        }

        StringBuilder sb = new StringBuilder();
        tree2strHelper(t, sb);

        return sb.toString();
    }

    private void tree2strHelper(TreeNode t, StringBuilder sb) {
        if (t == null) {
            return;
        }

        sb.append(t.val);
        if (t.left == null && t.right == null) {
            return;
        }

        sb.append('(');
        tree2strHelper(t.left, sb);
        sb.append(')');

        if (t.right != null) {
            sb.append('(');
            tree2strHelper(t.right, sb);
            sb.append(')');
        }
    }
}
