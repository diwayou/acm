package com.diwayou.acm.leetcode.lc700;

/**
 * https://leetcode-cn.com/problems/rotate-string/
 *
 * 给定两个字符串, A 和 B。
 * A 的旋转操作就是将 A 最左边的字符移动到最右边。 例如, 若 A = 'abcde'，在移动一次之后结果就是'bcdea' 。如果在若干次旋转操作之后，A 能变成B，那么返回True。
 *
 * 示例 1:
 * 输入: A = 'abcde', B = 'cdeab'
 * 输出: true
 *
 * 示例 2:
 * 输入: A = 'abcde', B = 'abced'
 * 输出: false
 *
 * 注意：
 * A 和 B 长度不超过 100。
 */
public class Lc796 {

    public static void main(String[] args) {
        System.out.println(new Lc796().rotateString("abcde", "cdeab"));
        System.out.println(new Lc796().rotateString("abcde", "abced"));
    }

    public boolean rotateString(String A, String B) {
        if (A.length() != B.length()) {
            return false;
        }
        if (A.isEmpty()) {
            return true;
        }

        char[] a = A.toCharArray();
        char[] b = B.toCharArray();

        char bFirst = b[0];

        int aNext, bNext;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == bFirst) {
                aNext = (i + 1) % a.length;
                bNext = 1;
                while (aNext != i) {
                    if (a[aNext] != b[bNext]) {
                        break;
                    }

                    aNext = (aNext + 1) % a.length;
                    bNext++;
                }

                if (aNext == i) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean rotateString1(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }

        return (a + a).contains(b);
    }
}
