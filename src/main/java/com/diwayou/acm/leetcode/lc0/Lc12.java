package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/integer-to-roman/
 * <p>
 * 罗马数字包含以下七种字符：I，V，X，L，C，D和M。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * <p>
 * 例如， 罗马数字 2 写做II，即为两个并列的 1。12 写做XII，即为X+II。 27 写做XXVII, 即为XX+V+II。
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV。数字 1 在数字 5 的左边，所表示的数等于大数
 * 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为IX。这个特殊的规则只适用于以下六种情况：
 * I可以放在V(5) 和X(10) 的左边，来表示 4 和 9。
 * X可以放在L(50) 和C(100) 的左边，来表示 40 和90。
 * C可以放在D(500) 和M(1000) 的左边，来表示400 和900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1到 3999 的范围内。
 * <p>
 * 示例1:
 * 输入:3
 * 输出: "III"
 * <p>
 * 示例2:
 * 输入:4
 * 输出: "IV"
 * <p>
 * 示例3:
 * 输入:9
 * 输出: "IX"
 * <p>
 * 示例4:
 * 输入:58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * <p>
 * 示例5:
 * 输入:1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 */
public class Lc12 {

    public static void main(String[] args) {
        System.out.println(new Lc12().intToRoman(1994));
    }

    public String intToRoman(int num) {
        int[] keys = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        String[] values = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

        StringBuilder re = new StringBuilder();
        while (num > 0) {
            for (int i = keys.length - 1; i >= 0; i--) {
                if (num >= keys[i]) {
                    num -= keys[i];
                    re.append(values[i]);
                    break;
                }
            }
        }

        return re.toString();
    }
}
