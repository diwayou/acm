package com.diwayou.acm.leetcode.lc400;

/**
 * https://leetcode-cn.com/problems/convert-a-number-to-hexadecimal/
 * <p>
 * 给定一个整数，编写一个算法将这个数转换为十六进制数。对于负整数，我们通常使用补码运算方法。
 * <p>
 * 注意:
 * 十六进制中所有字母(a-f)都必须是小写。
 * 十六进制字符串中不能包含多余的前导零。如果要转化的数为0，那么以单个字符'0'来表示；对于其他情况，十六进制字符串中的第一个字符将不会是0字符。
 * 给定的数确保在32位有符号整数范围内。
 * 不能使用任何由库提供的将数字直接转换或格式化为十六进制的方法。
 * <p>
 * 示例 1：
 * 输入:
 * 26
 * 输出:
 * "1a"
 * <p>
 * 示例 2：
 * 输入:
 * -1
 * 输出:
 * "ffffffff"
 */
public class Lc405 {

    public static void main(String[] args) {
        System.out.println(new Lc405().toHex(-1));
    }

    private static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public String toHex(int num) {
        if (num == 0) {
            return "0";
        }

        String s = "";
        while (num != 0) {
            s = hex[num & 15] + s;
            num >>>= 4;
        }

        return s;
    }
}
