package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/add-binary/
 */
public class Lc67 {

    public static void main(String[] args) {
        System.out.println(new Lc67().addBinary("11", "11"));
    }

    public String addBinary(String a, String b) {
        int i = a.length() - 1, j = b.length() - 1;

        StringBuilder re = new StringBuilder(Math.max(a.length(), b.length()) + 1);
        int p = 0;
        while (i >= 0 || j >= 0) {
            int m = i >= 0 ? a.charAt(i--) - '0' : 0;
            int n = j >= 0 ? b.charAt(j--) - '0' : 0;
            int t = m + n + p;

            re.append((char)(t % 2 + '0'));

            p = t / 2;
        }

        if (p > 0) {
            re.append('1');
        }

        return re.reverse().toString();
    }
}
