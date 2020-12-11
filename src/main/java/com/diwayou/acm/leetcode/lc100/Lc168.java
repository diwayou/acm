package com.diwayou.acm.leetcode.lc100;

/**
 * https://leetcode-cn.com/problems/excel-sheet-column-title/
 * <p>
 * 给定一个正整数，返回它在 Excel 表中相对应的列名称。
 * <p>
 * 例如，
 * 1 -> A
 * 2 -> B
 * 3 -> C
 * ...
 * 26 -> Z
 * 27 -> AA
 * 28 -> AB
 * ...
 * <p>
 * 示例 1:
 * 输入: 1
 * 输出: "A"
 * <p>
 * 示例2:
 * 输入: 28
 * 输出: "AB"
 * <p>
 * 示例3:
 * 输入: 701
 * 输出: "ZY"
 */
public class Lc168 {

    public static void main(String[] args) {
        System.out.println(new Lc168().convertToTitle(52));
    }

    public String convertToTitle(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        while (n != 0) {
            n--;
            stringBuilder.append((char) (n % 26 + 'A'));
            n /= 26;
        }

        return stringBuilder.reverse().toString();
    }
}
