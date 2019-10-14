package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/zigzag-conversion/
 * <p>
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 * <p>
 * 请你实现这个将字符串进行指定行数变换的函数：
 * <p>
 * string convert(string s, int numRows);
 * 示例 1:
 * 输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * <p>
 * 示例 2:
 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:
 * <p>
 * L     D     R
 * E   O E   I I
 * E C   I H   N
 * T     S     G
 */
public class Lc6 {

    public static void main(String[] args) {
        System.out.println(new Lc6().convert("LEETCODEISHIRING", 4));
    }

    public String convert1(String s, int numRows) {
        if (numRows <= 1) {
            return s;
        }

        StringBuilder[] sbs = new StringBuilder[numRows];
        for (int i = 0; i < sbs.length; i++) {
            sbs[i] = new StringBuilder();
        }

        int group = numRows * 2 - 2;
        int g, idx;
        for (int i = 0; i < s.length(); i++) {
            g = i % group;
            idx = g < numRows ? g : (group - g);

            sbs[idx].append(s.charAt(i));
        }

        StringBuilder re = new StringBuilder(s.length());
        for (int i = 0; i < sbs.length; i++) {
            re.append(sbs[i]);
        }

        return re.toString();
    }

    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }

        StringBuilder ret = new StringBuilder();
        int n = s.length();
        int cycleLen = 2 * numRows - 2;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < n; j += cycleLen) {
                ret.append(s.charAt(j + i));
                if (i != 0 && i != numRows - 1 && j + cycleLen - i < n) {
                    ret.append(s.charAt(j + cycleLen - i));
                }
            }
        }

        return ret.toString();
    }
}
