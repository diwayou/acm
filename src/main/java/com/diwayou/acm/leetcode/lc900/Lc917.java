package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/reverse-only-letters/
 *
 * 给定一个字符串 S，返回 “反转后的” 字符串，其中不是字母的字符都保留在原地，而所有字母的位置发生反转。
 *
 * 示例 1：
 * 输入："ab-cd"
 * 输出："dc-ba"
 *
 * 示例 2：
 * 输入："a-bC-dEf-ghIj"
 * 输出："j-Ih-gfE-dCba"
 *
 * 示例 3：
 * 输入："Test1ng-Leet=code-Q!"
 * 输出："Qedo1ct-eeLg=ntse-T!"
 *  
 * 提示：
 * S.length <= 100
 * 33 <= S[i].ASCIIcode <= 122 
 * S 中不包含 \ or "
 */
public class Lc917 {

    public String reverseOnlyLetters(String S) {
        char[] sa = S.toCharArray();
        int i = 0, j = sa.length - 1;
        while (i < j) {
            while (i < sa.length && !Character.isLetter(sa[i])) {
                i++;
            }
            while (j >= 0 && !Character.isLetter(sa[j])) {
                j--;
            }

            if (i >= j) {
                break;
            }

            char c = sa[i];
            sa[i] = sa[j];
            sa[j] = c;

            i++;
            j--;
        }

        return new String(sa);
    }
}
