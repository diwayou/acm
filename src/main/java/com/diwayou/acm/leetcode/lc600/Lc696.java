package com.diwayou.acm.leetcode.lc600;

/**
 * https://leetcode-cn.com/problems/count-binary-substrings/
 *
 * 给定一个字符串s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
 * 重复出现的子串要计算它们出现的次数。
 *
 * 示例 1 :
 * 输入: "00110011"
 * 输出: 6
 * 解释: 有6个子串具有相同数量的连续1和0：“0011”，“01”，“1100”，“10”，“0011” 和 “01”。
 * 请注意，一些重复出现的子串要计算它们出现的次数。
 * 另外，“00110011”不是有效的子串，因为所有的0（和1）没有组合在一起。
 *
 * 示例 2 :
 * 输入: "10101"
 * 输出: 4
 * 解释: 有4个子串：“10”，“01”，“10”，“01”，它们具有相同数量的连续1和0。
 *
 * 注意：
 * s.length在1到50,000之间。
 * s只包含“0”或“1”字符。
 */
public class Lc696 {

    public static void main(String[] args) {
        System.out.println(new Lc696().countBinarySubstrings("0001110011"));
    }

    public int countBinarySubstrings(String s) {
        int re = 0, j, ac, bc;
        char[] ca = s.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            ac = 1;
            j = i + 1;
            while (j < ca.length && ca[j] == ca[j - 1]) {
                ac++;
                j++;
            }
            if (j >= ca.length) {
                continue;
            }

            bc = 1;
            j++;
            while (bc < ac && j < ca.length && ca[j] == ca[j - 1]) {
                bc++;
                j++;
            }

            if (ac == bc) {
                re++;
            }
        }

        return re;
    }

    public int countBinarySubstrings1(String s) {
        char[] chars = s.toCharArray();
        int count = 1, pre = 0;
        int res = 0;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i - 1] == chars[i]) {
                count++;
            } else {
                res += Math.min(pre, count);
                pre = count;
                count = 1;
            }
        }

        res += Math.min(pre, count);
        return res;
    }
}
