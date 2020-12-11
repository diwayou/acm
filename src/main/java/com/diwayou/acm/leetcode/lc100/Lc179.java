package com.diwayou.acm.leetcode.lc100;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/largest-number/
 * <p>
 * 给定一组非负整数，重新排列它们的顺序使之组成一个最大的整数。
 * <p>
 * 示例 1:
 * 输入: [10,2]
 * 输出: 210
 * <p>
 * 示例2:
 * 输入: [3,30,34,5,9]
 * 输出: 9534330
 * 说明: 输出结果可能非常大，所以你需要返回一个字符串而不是整数。
 */
public class Lc179 {

    public static void main(String[] args) {
        System.out.println(new Lc179().largestNumber(new int[]{3, 30, 34, 5, 9}));
        System.out.println(new Lc179().largestNumber(new int[]{121, 12}));
        System.out.println(new Lc179().largestNumber(new int[]{1440, 7548, 4240, 6616, 733, 4712, 883, 8, 9576}));
    }

    public String largestNumber(int[] nums) {
        boolean nz = false;
        for (int i : nums) {
            if (i != 0) {
                nz = true;
                break;
            }
        }
        if (!nz) {
            return "0";
        }

        return Arrays.stream(nums)
                .mapToObj(String::valueOf)
                .sorted(new LargerNumberComparator())
                .collect(Collectors.joining());
    }

    private class LargerNumberComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            String order1 = a + b;
            String order2 = b + a;
            return order2.compareTo(order1);
        }
    }

    public String largestNumber1(int[] nums) {
        // Get input integers as strings.
        String[] asStrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            asStrs[i] = String.valueOf(nums[i]);
        }

        // Sort strings according to custom comparator.
        Arrays.sort(asStrs, new LargerNumberComparator());

        // If, after being sorted, the largest number is `0`, the entire number
        // is zero.
        if (asStrs[0].equals("0")) {
            return "0";
        }

        // Build largest number from sorted array.
        String largestNumberStr = new String();
        for (String numAsStr : asStrs) {
            largestNumberStr += numAsStr;
        }

        return largestNumberStr;
    }
}
