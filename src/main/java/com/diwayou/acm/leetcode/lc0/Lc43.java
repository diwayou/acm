package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/multiply-strings/
 */
public class Lc43 {

    public static void main(String[] args) {
        System.out.println(new Lc43().multiply("3", "39"));
    }

    public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }

        char[] char1 = num1.toCharArray();
        char[] char2 = num2.toCharArray();

        int len1 = char1.length;
        int len2 = char2.length;

        int[] res = new int[len1 + len2];

        int[] arr1 = new int[len1];
        int[] arr2 = new int[len2];

        for (int i = 0; i < len1; i++) {
            arr1[i] = char1[i] - '0';
        }
        for (int i = 0; i < len2; i++) {
            arr2[i] = char2[i] - '0';
        }

        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                res[i + j] += arr1[i] * arr2[j];
            }
        }

        //对结果进行处理
        for (int i = res.length - 1; i > 0; i--) {
            res[i - 1] = res[i - 1] + res[i] / 10;
            res[i] = res[i] % 10;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < res.length - 1; i++) {
            sb.append(res[i]);
        }

        return sb.toString();
    }
}
