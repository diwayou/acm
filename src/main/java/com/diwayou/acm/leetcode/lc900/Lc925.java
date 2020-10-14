package com.diwayou.acm.leetcode.lc900;

/**
 * https://leetcode-cn.com/problems/long-pressed-name/
 *
 * 你的朋友正在使用键盘输入他的名字name。偶尔，在键入字符c时，按键可能会被长按，而字符可能被输入 1 次或多次。
 * 你将会检查键盘输入的字符typed。如果它对应的可能是你的朋友的名字（其中一些字符可能被长按），那么就返回True。
 *
 * 示例 1：
 * 输入：name = "alex", typed = "aaleex"
 * 输出：true
 * 解释：'alex' 中的 'a' 和 'e' 被长按。
 *
 * 示例 2：
 * 输入：name = "saeed", typed = "ssaaedd"
 * 输出：false
 * 解释：'e' 一定需要被键入两次，但在 typed 的输出中不是这样。
 *
 * 示例 3：
 * 输入：name = "leelee", typed = "lleeelee"
 * 输出：true
 *
 * 示例 4：
 * 输入：name = "laiden", typed = "laiden"
 * 输出：true
 * 解释：长按名字中的字符并不是必要的。
 * 
 * 提示：
 * name.length <= 1000
 * typed.length <= 1000
 * name 和typed的字符都是小写字母。
 */
public class Lc925 {

    public static void main(String[] args) {
        System.out.println(new Lc925().isLongPressedName("ntrmbxkkadwntdivvgau"
                ,"nttrrmbxxkkkkadwwnttddivvvgau"));
    }

    public boolean isLongPressedName(String name, String typed) {
        if (typed.length() < name.length()) {
            return false;
        }
        if (name.isEmpty()) {
            return true;
        }
        if (name.charAt(0) != typed.charAt(0)) {
            return false;
        }

        char[] na = name.toCharArray();
        char[] ta = typed.toCharArray();
        int i, j;
        for (i = 0, j = 0; i < na.length && j < ta.length; ) {
            if (na[i] == ta[j]) {
                i++;
            } else {
                if (j < ta.length - 1 && ta[j + 1] != ta[j] && ta[j + 1] != na[i]) {
                    return false;
                }
            }
            j++;
        }

        if (i != na.length) {
            return false;
        }
        if (j < ta.length) {
            while (j < ta.length) {
                if (ta[j] == ta[j - 1]) {
                    j++;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isLongPressedName1(String name, String typed) {
        char[] na = name.toCharArray();
        char[] ta = typed.toCharArray();

        int i = 0, j = 0;
        char c = na[0];
        while (i < na.length && j < ta.length) {
            if (c == ta[j]) {
                if (c == na[i]) {
                    j++;
                    i++;
                } else {
                    j++;
                }
            } else if (c != ta[j]) {
                if (ta[j] == na[i])
                    c = na[i];
                else return false;
            }
        }

        return i == na.length;
    }
}
