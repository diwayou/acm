package com.diwayou.acm.leetcode.lc100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/pascals-triangle/
 * <p>
 * 0
 * 0 1
 * 0 1 2
 * 0 1 2 3
 */
public class Lc118 {

    public static void main(String[] args) {
        System.out.println(new Lc118().generate(5));
    }

    public List<List<Integer>> generate(int numRows) {
        if (numRows == 0) {
            return Collections.emptyList();
        }
        if (numRows == 1) {
            return Collections.singletonList(Collections.singletonList(1));
        }

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> row;
        int cols = 1, i;
        int r = 0;
        while (r < numRows) {
            row = new ArrayList<>(cols);
            row.add(1);
            for (i = 1; i < cols - 1; i++) {
                row.add(result.get(r - 1).get(i) + result.get(r - 1).get(i - 1));
            }
            if (i < cols) {
                row.add(1);
            }

            cols++;
            result.add(row);
            r++;
        }

        return result;
    }
}
