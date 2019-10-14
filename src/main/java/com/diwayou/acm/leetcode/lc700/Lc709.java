package com.diwayou.acm.leetcode.lc700;

/**
 * https://leetcode-cn.com/problems/to-lower-case/
 */
public class Lc709 {

    public static void main(String[] args) {
        System.out.println(new Lc709().toLowerCase("Abc"));
        System.out.println(new Lc709().toLowerCase("aBc"));
        System.out.println(new Lc709().toLowerCase("ABC"));
    }

    public String toLowerCase(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        char ch;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                ch = (char)(ch - 'A' + 'a');
            }

            sb.append(ch);
        }

        return sb.toString();
    }
}
