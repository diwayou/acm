package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/decode-ways/
 * <p>
 * 一条包含字母A-Z 的消息通过以下方式进行了编码：
 * <p>
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * <p>
 * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
 * <p>
 * 示例 1:
 * 输入: "12"
 * 输出: 2
 * 解释:它可以解码为 "AB"（1 2）或者 "L"（12）。
 * <p>
 * 示例2:
 * 输入: "226"
 * 输出: 3
 * 解释:它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
 */
public class Lc91 {

    public static void main(String[] args) {
        System.out.println(new Lc91().numDecodings("12121200"));
    }

    public int numDecodings(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        if (s.charAt(0) == '0') {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }

        char[] sa = s.toCharArray();
        int a = 1, b = 1, t;
        for (int i = 1; i < sa.length; i++) {
            t = 0;
            if (sa[i] != '0') {
                t = b;
            }
            if ((sa[i - 1] == '2' && sa[i] >= '0' && sa[i] <= '6') || (sa[i - 1] == '1' && sa[i] >= '0' && sa[i] <= '9')) {
                t += a;
            }

            if (t == 0) {
                return 0;
            }

            a = b;
            b = t;
        }

        return b;
    }
}
