package com.diwayou.acm.leetcode.lc1200;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/unique-number-of-occurrences/
 *
 * 给你一个整数数组 arr，请你帮忙统计数组中每个数的出现次数。
 * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
 *
 * 示例 1：
 * 输入：arr = [1,2,2,1,1,3]
 * 输出：true
 * 解释：在该数组中，1 出现了 3 次，2 出现了 2 次，3 只出现了 1 次。没有两个数的出现次数相同。
 *
 * 示例 2：
 * 输入：arr = [1,2]
 * 输出：false
 *
 * 示例 3：
 * 输入：arr = [-3,0,1,-3,1,1,1,-3,10,0]
 * 输出：true
 *
 * 提示：
 * 1 <= arr.length <= 1000
 * -1000 <= arr[i] <= 1000
 */
public class Lc1207 {

    public static void main(String[] args) {
        System.out.println(new Lc1207().uniqueOccurrences(new int[]{1, 2}));
    }

    public boolean uniqueOccurrences(int[] arr) {
        if (arr.length == 1) {
            return true;
        }

        Map<Integer, Integer> cm = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            cm.put(arr[i], cm.getOrDefault(arr[i], 0) + 1);
        }

        Set<Integer> s = new HashSet<>();
        for (Integer i : cm.values()) {
            if (s.contains(i)) {
                return false;
            }

            s.add(i);
        }

        return true;
    }
}
