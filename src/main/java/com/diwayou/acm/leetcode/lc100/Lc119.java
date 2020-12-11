package com.diwayou.acm.leetcode.lc100;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/pascals-triangle-ii/
 * <p>
 * 给定一个非负索引k，其中 k≤33，返回杨辉三角的第 k 行。
 * 在杨辉三角中，每个数是它左上方和右上方的数的和。
 * <p>
 * 示例:
 * 输入: 3
 * 输出: [1,3,3,1]
 * <p>
 * 进阶：
 * 你可以优化你的算法到 O(k) 空间复杂度吗？
 */
public class Lc119 {

    public List<Integer> getRow(int rowIndex) {
        List<Integer> re = new ArrayList<>(rowIndex + 1);
        for (int i = 0; i <= rowIndex; ++i) {
            re.add(1);
            for (int j = i - 1; j > 0; --j) {
                re.set(j, re.get(j) + re.get(j - 1));
            }
        }

        return re;
    }

    public List<Integer> getRow1(int rowIndex) {
        int[] result = new int[rowIndex + 1];
        result[0] = 1;
        for (int i = 1; i <= rowIndex; i++) {
            for (int j = i - 1; j > 0; j--) {
                result[j] += result[j - 1];
            }
            result[i] = 1;
        }

        List<Integer> re = new ArrayList<>(result.length);
        for (int n : result) {
            re.add(n);
        }

        return re;
    }
}
