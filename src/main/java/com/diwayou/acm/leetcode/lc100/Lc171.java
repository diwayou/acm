package com.diwayou.acm.leetcode.lc100;

/**
 * https://leetcode-cn.com/problems/excel-sheet-column-number/
 *
 * 给定一个Excel表格中的列名称，返回其相应的列序号。
 *
 * 例如，
 *     A -> 1
 *     B -> 2
 *     C -> 3
 *     ...
 *     Z -> 26
 *     AA -> 27
 *     AB -> 28
 *     ...
 *
 * 示例 1:
 * 输入: "A"
 * 输出: 1
 *
 * 示例2:
 * 输入: "AB"
 * 输出: 28
 *
 * 示例3:
 * 输入: "ZY"
 * 输出: 701
 */
public class Lc171 {

    public static void main(String[] args) {
        System.out.println(new Lc171().titleToNumber("ZY"));
    }

    public int titleToNumber(String s) {
        int re = 0;
        for (char c : s.toCharArray()) {
            re *= 26;
            re += c - 'A' + 1;
        }

        return re;
    }
}
