package com.diwayou.acm.leetcode.lc1000;

/**
 * https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string/
 *
 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 *
 * 示例：
 * 输入："abbaca"
 * 输出："ca"
 * 解释：
 * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，
 * 其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
 *  
 * 提示：
 * 1 <= S.length <= 20000
 * S 仅由小写英文字母组成。
 */
public class Lc1047 {

    public static void main(String[] args) {
        System.out.println(new Lc1047().removeDuplicates("abbaca"));
    }

    public String removeDuplicates(String S) {
        if (S.length() < 2) {
            return S;
        }

        char[] ca = S.toCharArray();

        int len = removeDuplicatesHelper(ca, ca.length);

        return new String(ca, 0, len);
    }

    private int removeDuplicatesHelper(char[] ca, int len) {
        int newLen = len;
        for (int i = 0; i < len - 1; i++) {
            if (ca[i] == ca[i + 1]) {
                newLen = len - 2;
                if (i + 2 < len) {
                    System.arraycopy(ca, i + 2, ca, i, len - (i + 2));
                }
                break;
            }
        }

        if (newLen != len) {
            newLen = removeDuplicatesHelper(ca, newLen);
        }

        return newLen;
    }

    public String removeDuplicates1(String S) {
        if (S.length() < 2) {
            return S;
        }

        int i = 0, n = S.length();
        char[] res = S.toCharArray();
        for (int j = 0; j < n; ++j, ++i) {
            res[i] = res[j];
            if (i > 0 && res[i - 1] == res[i]) // count = 2
                i -= 2;
        }

        return new String(res, 0, i);
    }
}
