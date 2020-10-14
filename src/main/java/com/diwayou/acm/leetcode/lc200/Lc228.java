package com.diwayou.acm.leetcode.lc200;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/summary-ranges/
 *
 * 给定一个无重复元素的有序整数数组，返回数组区间范围的汇总。
 *
 * 示例 1:
 * 输入: [0,1,2,4,5,7]
 * 输出: ["0->2","4->5","7"]
 * 解释: 0,1,2 可组成一个连续的区间;4,5 可组成一个连续的区间。
 *
 * 示例 2:
 * 输入: [0,2,3,4,6,8,9]
 * 输出: ["0","2->4","6","8->9"]
 * 解释: 2,3,4 可组成一个连续的区间;8,9 可组成一个连续的区间。
 */
public class Lc228 {

    public static void main(String[] args) {
        System.out.println(new Lc228().summaryRanges(new int[]{0,2,3,4,6,8,9}));
    }

    public List<String> summaryRanges(int[] nums) {
        if (nums.length == 0) {
            return Collections.emptyList();
        }

        List<String> re = new ArrayList<>();
        int s = nums[0], e = s;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                e = nums[i];
            } else {
                if (s == e) {
                    re.add(String.valueOf(s));
                } else {
                    re.add(s + "->" + e);
                }

                s = nums[i];
                e = s;
            }
        }

        if (s == e) {
            re.add(String.valueOf(s));
        } else {
            re.add(s + "->" + e);
        }

        return re;
    }
}
