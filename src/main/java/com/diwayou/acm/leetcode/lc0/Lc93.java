package com.diwayou.acm.leetcode.lc0;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/restore-ip-addresses/
 * <p>
 * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
 * <p>
 * 示例:
 * 输入: "25525511135"
 * 输出: ["255.255.11.135", "255.255.111.35"]
 */
public class Lc93 {

    public static void main(String[] args) {
        System.out.println(new Lc93().restoreIpAddresses("25525511135"));
    }

    public List<String> restoreIpAddresses1(String s) {
        List<String> re = new ArrayList<>();
        parse(s, 0, s.length(), 3, "", re);

        return re;
    }

    private void parse(String sa, int i, int remain, int level, String cur, List<String> re) {
        if (level < 0) {
            re.add(cur);
            return;
        }

        int s = Math.max(1, remain - level * 3);
        int e = Math.min(3, remain - level);

        for (int j = s; j <= e; j++) {
            String t = sa.substring(i, i + j);
            int v = Integer.parseInt(t);
            if (v > 255 || (t.length() == 3 && v < 100) || (t.length() == 2 && v < 10)) {
                continue;
            }

            parse(sa, i + j, remain - j, level - 1, level != 3 ? cur + "." + t : cur + t, re);
        }
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> re = new ArrayList<>();
        StringBuilder backtrack = new StringBuilder(16);
        restore(s.toCharArray(), 0, s.length(), 3, backtrack, re);

        return re;
    }

    private void restore(char[] sa, int i, int remain, int level, StringBuilder backtrack, List<String> re) {
        if (level < 0) {
            re.add(backtrack.toString());
            return;
        }

        int s = Math.max(1, remain - level * 3);
        int e = Math.min(3, remain - level);

        if (level != 3) {
            backtrack.append('.');
        }

        int len = backtrack.length();
        for (int j = s; j <= e; j++) {
            if ((j > 1 && sa[i] == '0') || (j == 3 && (sa[i] - '0') * 100 + (sa[i + 1] - '0') * 10 + sa[i + 2] - '0' > 255)) {
                continue;
            }

            backtrack.append(sa, i, j);
            restore(sa, i + j, remain - j, level - 1, backtrack, re);
            backtrack.delete(len, backtrack.length());
        }

        if (level != 3) {
            backtrack.deleteCharAt(backtrack.length() - 1);
        }
    }
}
