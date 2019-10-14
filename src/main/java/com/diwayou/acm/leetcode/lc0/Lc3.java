package com.diwayou.acm.leetcode.lc0;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 * <p>
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class Lc3 {

    public static void main(String[] args) {
        System.out.println(new Lc3().lengthOfLongestSubstring("aabaab!bb"));
    }

    // 想法已经比较接近最优解了，但是还不够抽象，那个last的管理
    public int lengthOfLongestSubstring1(String s) {
        if (s.isEmpty()) {
            return 0;
        }

        int max = 1;
        int[] arr = new int[256];
        Arrays.fill(arr, -1);
        int l, last = -1, idx;
        arr[s.charAt(0)] = 0;
        for (int i = 1; i < s.length(); i++) {
            idx = s.charAt(i);
            l = i - Math.max(arr[idx], last);
            if (l > max) {
                max = l;
            }

            if (arr[idx] != -1) {
                last = Math.max(arr[idx], last);
            }
            arr[idx] = i;
        }

        return max;
    }

    // 滑动窗口的思维一定要放到工具箱里
    public int lengthOfLongestSubstring(String s) {
        if (s.isEmpty()) {
            return 0;
        }

        int l = 0, r = 0, result = 1;
        int[] t = new int[256];
        char[] chars = s.toCharArray();
        while (r < chars.length) {
            if (t[chars[r]] == 0) {
                t[chars[r++]]++;
            } else {
                t[chars[l++]]--;
            }

            result = Math.max(r - l, result);
        }

        return result;
    }
}
