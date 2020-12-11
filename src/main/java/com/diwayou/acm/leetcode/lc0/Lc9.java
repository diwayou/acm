package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/palindrome-number/
 */
public class Lc9 {

    public boolean isPalindrome1(int x) {
        if (x < 0) {
            return false;
        }
        if (x == 0) {
            return true;
        }

        StringBuilder sb = new StringBuilder();
        while (x != 0) {
            sb.append((char) (x % 10 + '0'));
            x /= 10;
        }

        return sb.toString().equals(sb.reverse().toString());
    }

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x == 0) {
            return true;
        }

        int re = 0, c = x;
        while (c != 0) {
            re = re * 10 + c % 10;
            c /= 10;
        }

        return x == re;
    }
}
