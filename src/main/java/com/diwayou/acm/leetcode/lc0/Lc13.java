package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/roman-to-integer/
 * <p>
 * 罗马数字包含以下七种字符:I，V，X，L，C，D和M。
 * <p>
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
 * 给定一个罗马数字，将其转换成整数。输入确保在 1到 3999 的范围内。
 * <p>
 * 示例1:
 * 输入:"III"
 * 输出: 3
 * <p>
 * 示例2:
 * 输入:"IV"
 * 输出: 4
 * <p>
 * 示例3:
 * 输入:"IX"
 * 输出: 9
 * <p>
 * 示例4:
 * 输入:"LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3.
 * 示例5:
 * <p>
 * 输入:"MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 */
public class Lc13 {

    public static void main(String[] args) {
        System.out.println(new Lc13().romanToInt("MCMXCIV"));
    }

    public int romanToInt(String s) {
        int res = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            switch (c) {
                case 'I':
                    res += (res >= 5 ? -1 : 1);
                    break;
                case 'V':
                    res += 5;
                    break;
                case 'X':
                    res += 10 * (res >= 50 ? -1 : 1);
                    break;
                case 'L':
                    res += 50;
                    break;
                case 'C':
                    res += 100 * (res >= 500 ? -1 : 1);
                    break;
                case 'D':
                    res += 500;
                    break;
                case 'M':
                    res += 1000;
                    break;
            }
        }

        return res;
    }

    public int romanToInt1(String s) {
        if (s.isEmpty()) {
            return 0;
        }

        int i = 0;
        int re = 0;
        char c, n;
        while (i < s.length()) {
            c = s.charAt(i);
            if (i == s.length() - 1) {
                re += v(c);
                break;
            }

            n = s.charAt(i + 1);
            if ((c == 'I' && (n == 'V' || n == 'X')) ||
                    (c == 'X' && (n == 'L' || n == 'C') ||
                            (c == 'C' && (n == 'D' || n == 'M')))) {
                re += v(n) - v(c);
                i += 2;
            } else {
                re += v(c);
                i++;
            }
        }

        return re;
    }

    private static int v(char c) {
        if (c == 'I') {
            return 1;
        } else if (c == 'V') {
            return 5;
        } else if (c == 'X') {
            return 10;
        } else if (c == 'L') {
            return 50;
        } else if (c == 'C') {
            return 100;
        } else if (c == 'D') {
            return 500;
        } else if (c == 'M') {
            return 1000;
        }

        return 0;
    }
}
