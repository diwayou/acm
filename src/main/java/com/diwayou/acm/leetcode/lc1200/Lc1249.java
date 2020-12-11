package com.diwayou.acm.leetcode.lc1200;

/**
 * https://leetcode-cn.com/problems/minimum-remove-to-make-valid-parentheses/
 * <p>
 * 给你一个由 '('、')' 和小写字母组成的字符串 s。
 * 你需要从字符串中删除最少数目的 '(' 或者 ')'（可以删除任意位置的括号)，使得剩下的「括号字符串」有效。
 * 请返回任意一个合法字符串。
 * 有效「括号字符串」应当符合以下任意一条要求：
 * 空字符串或只包含小写字母的字符串
 * 可以被写作AB（A连接B）的字符串，其中A和B都是有效「括号字符串」
 * 可以被写作(A)的字符串，其中A是一个有效的「括号字符串」
 * <p>
 * 示例 1：
 * 输入：s = "lee(t(c)o)de)"
 * 输出："lee(t(c)o)de"
 * 解释："lee(t(co)de)" , "lee(t(c)ode)" 也是一个可行答案。
 * <p>
 * 示例 2：
 * 输入：s = "a)b(c)d"
 * 输出："ab(c)d"
 * <p>
 * 示例 3：
 * 输入：s = "))(("
 * 输出：""
 * 解释：空字符串也是有效的
 * <p>
 * 示例 4：
 * 输入：s = "(a(b(c)d)"
 * 输出："a(b(c)d)"
 * <p>
 * 提示：
 * 1 <= s.length <= 10^5
 * s[i]可能是'('、')'或英文小写字母
 */
public class Lc1249 {

    public static void main(String[] args) {
        System.out.println(new Lc1249().minRemoveToMakeValid(")((c)d()(l"));
    }

    public String minRemoveToMakeValid(String s) {
        char[] sa = s.toCharArray();

        int ps = 0;
        int[] stack = new int[sa.length];
        for (int i = 0; i < sa.length; i++) {
            if (sa[i] == ')') {
                stack[ps--] = i;
            } else if (sa[i] == '(') {
                stack[ps++] = i;
            }

            if (ps < 0) {
                sa[i] = '\0';
                ps = 0;
            }
        }

        if (ps != 0) {
            for (int i = 0; i < ps; i++) {
                sa[stack[i]] = '\0';
            }
        }

        StringBuilder re = new StringBuilder(s.length());
        for (int i = 0; i < sa.length; i++) {
            if (sa[i] != '\0') {
                re.append(sa[i]);
            }
        }

        return re.toString();
    }

    public String minRemoveToMakeValid1(String s) {
        char[] cs = s.toCharArray();
        int remove = 0;
        int left = 0;
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == '(') {
                left++;
            } else if (cs[i] == ')') {
                if (left == 0) {
                    remove++;
                    cs[i] = '\0';
                } else {
                    left--;
                }
            }
        }
        int right = 0;
        for (int i = cs.length - 1; i >= 0; i--) {
            if (cs[i] == '(') {
                if (right == 0) {
                    remove++;
                    cs[i] = '\0';
                } else {
                    right--;
                }
            } else if (cs[i] == ')') {
                right++;
            }
        }
        char[] res = new char[cs.length - remove];
        int index = 0;
        for (char c : cs) {
            if (c != '\0') {
                res[index++] = c;
            }
        }

        return new String(res);
    }
}
