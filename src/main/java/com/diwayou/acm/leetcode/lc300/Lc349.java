package com.diwayou.acm.leetcode.lc300;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-arrays/
 *
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1:
 * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出: [2]
 *
 * 示例 2:
 * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出: [9,4]
 *
 * 说明:
 * 输出结果中的每个元素一定是唯一的。
 * 我们可以不考虑输出结果的顺序。
 */
public class Lc349 {

    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0) {
            return new int[0];
        }

        Set<Integer> s1 = new HashSet<>();
        for (int n : nums1) {
            s1.add(n);
        }
        Set<Integer> s2 = new HashSet<>();
        for (int n : nums2) {
            s2.add(n);
        }

        s1.retainAll(s2);

        int[] re = new int[s1.size()];
        Iterator<Integer> iter = s1.iterator();
        for (int i = 0; i < re.length; i++) {
            re[i] = iter.next();
        }

        return re;
    }
}
