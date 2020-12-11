package com.diwayou.acm.leetcode.lc300;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode-cn.com/problems/decode-string/
 * <p>
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像3a或2[4]的输入。
 * <p>
 * 示例:
 * s = "3[a]2[bc]", 返回 "aaabcbc".
 * s = "3[a2[c]]", 返回 "accaccacc".
 * s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
 */
public class Lc394 {

    public static void main(String[] args) {
        System.out.println(new Lc394().decodeString("3[a]2[bc]"));
        System.out.println(new Lc394().decodeString("3[a2[c]]"));
        System.out.println(new Lc394().decodeString("2[abc]3[cd]ef"));
        System.out.println(new Lc394().decodeString("100[leetcode]"));
    }

    public String decodeString(String s) {
        StringBuilder re = new StringBuilder(), tsb;
        char[] sc = s.toCharArray();
        Deque<Integer> stack = new LinkedList<>();
        Deque<StringBuilder> bstack = new LinkedList<>();
        int cnt;
        for (int i = 0; i < sc.length; i++) {
            if (sc[i] > '0' && sc[i] <= '9') {
                int num = sc[i] - '0';
                int j = i + 1;
                while (sc[j] >= '0' && sc[j] <= '9') {
                    num = num * 10 + sc[j] - '0';
                    j++;
                }
                stack.add(num);
                i = j - 1;
            } else if (sc[i] == '[') {
                bstack.add(new StringBuilder());
            } else if (sc[i] == ']') {
                cnt = stack.removeLast();
                tsb = bstack.removeLast();
                String st = tsb.toString();
                for (int j = 1; j < cnt; j++) {
                    tsb.append(st);
                }
                if (stack.isEmpty()) {
                    re.append(tsb);
                } else {
                    bstack.getLast().append(tsb);
                }
            } else {
                if (stack.isEmpty()) {
                    re.append(sc[i]);
                } else {
                    bstack.getLast().append(sc[i]);
                }
            }
        }

        return re.toString();
    }
}
