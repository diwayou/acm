package com.diwayou.acm.leetcode.lc400;

import com.diwayou.acm.leetcode.common.TreeNode;

import java.util.HashMap;

/**
 * https://leetcode-cn.com/problems/path-sum-iii/
 * <p>
 * 给定一个二叉树，它的每个结点都存放着一个整数值。
 * 找出路径和等于给定数值的路径总数。
 * 路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * 二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。
 * <p>
 * 示例：
 * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 * <p>
 * 10
 * /  \
 * 5   -3
 * / \    \
 * 3   2   11
 * / \   \
 * 3  -2   1
 * <p>
 * 返回 3。和等于 8 的路径有:
 * <p>
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3.  -3 -> 11
 */
public class Lc437 {

    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        return paths(root, sum)
                + pathSum(root.left, sum)
                + pathSum(root.right, sum);
    }

    private int paths(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        int res = 0;
        if (root.val == sum) {
            res += 1;
        }

        res += paths(root.left, sum - root.val);
        res += paths(root.right, sum - root.val);

        return res;
    }

    public int pathSum1(TreeNode root, int sum) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        return subSum(root, 0, sum, map);
    }

    private int subSum(TreeNode root, int sum, int target, HashMap<Integer, Integer> map) {
        if (root == null) {
            return 0;
        }

        sum += root.val;
        int count = map.getOrDefault(sum - target, 0);  //相当于：if (map.contains(sum - target)) 时，count 等于 sum - target对应的数出现的次数; 否则等于0。
        int temp = map.getOrDefault(sum, 0);
        map.put(sum, temp + 1);    //相当于：只有从根结点开始累加的和才能参与计数（因为一旦找到对应的path，就将删掉这部分和）。
        count += subSum(root.left, sum, target, map) + subSum(root.right, sum, target, map);
        map.put(sum, temp);    //相当于：这条path走完后就删了

        return count;
    }
}
