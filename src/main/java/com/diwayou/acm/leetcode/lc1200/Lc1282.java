package com.diwayou.acm.leetcode.lc1200;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/group-the-people-given-the-group-size-they-belong-to/
 *
 * 有 n 位用户参加活动，他们的 ID 从 0 到 n - 1，每位用户都 恰好 属于某一用户组。给你一个长度为 n 的数组 groupSizes，其中包含每位用户所处的
 * 用户组的大小，请你返回用户分组情况（存在的用户组以及每个组中用户的 ID）。
 *
 * 你可以任何顺序返回解决方案，ID 的顺序也不受限制。此外，题目给出的数据保证至少存在一种解决方案。
 *
 * 示例 1：
 * 输入：groupSizes = [3,3,3,3,3,1,3]
 * 输出：[[5],[0,1,2],[3,4,6]]
 * 解释：
 * 其他可能的解决方案有 [[2,1,6],[5],[0,4,3]] 和 [[5],[0,6,2],[4,3,1]]。
 *
 * 示例 2：
 * 输入：groupSizes = [2,1,3,3,3,2]
 * 输出：[[1],[0,5],[2,3,4]]
 *
 * 提示：
 * groupSizes.length == n
 * 1 <= n <= 500
 * 1 <= groupSizes[i] <= n
 *
 * @author gaopeng
 * @date 2020/10/10
 */
public class Lc1282 {

    public static void main(String[] args) {
        System.out.println(new Lc1282().groupThePeople(new int[]{1}));
    }

    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        List[] groups = new ArrayList[groupSizes.length + 1];

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < groupSizes.length; i++) {
            int size = groupSizes[i];
            List<Integer> group = groups[size];
            if (group == null) {
                groups[size] = new ArrayList();
            }
            groups[size].add(i);

            if (groups[size].size() == size) {
                result.add(groups[size]);
                groups[size] = null;
            }
        }

        return result;
    }
}
