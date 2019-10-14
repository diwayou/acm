package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/reverse-integer/
 */
public class Lc7 {

    public static void main(String[] args) {
        System.out.println(new Lc7().reverse(1234567899));
    }

    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;

            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }

            rev = rev * 10 + pop;
        }

        return rev;
    }
}
