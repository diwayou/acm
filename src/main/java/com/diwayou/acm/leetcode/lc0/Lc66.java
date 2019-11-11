package com.diwayou.acm.leetcode.lc0;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/plus-one/
 *
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 * 示例 1:
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * 解释: 输入数组表示数字 123。
 *
 * 示例 2:
 * 输入: [4,3,2,1]
 * 输出: [4,3,2,2]
 * 解释: 输入数组表示数字 4321。
 */
public class Lc66 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc66().plusOne(new int[]{9, 9, 9})));
    }

    public int[] plusOne(int[] digits) {
        int carry = 1, t;
        for (int i = digits.length - 1; i >= 0; i--) {
            t = digits[i] + carry;
            if (t > 9) {
                carry = 1;
                digits[i] = t % 10;
            } else {
                carry = 0;
                digits[i] = t;
                break;
            }
        }

        if (carry == 0) {
            return digits;
        }

        int[] re = new int[digits.length + 1];
        re[0] = 1;
        System.arraycopy(digits, 0, re, 1, digits.length);

        return re;
    }
}
