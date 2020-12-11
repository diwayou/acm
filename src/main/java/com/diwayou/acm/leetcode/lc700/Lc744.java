package com.diwayou.acm.leetcode.lc700;

/**
 * https://leetcode-cn.com/problems/find-smallest-letter-greater-than-target/
 * <p>
 * 给定一个只包含小写字母的有序数组letters和一个目标字母target，寻找有序数组里面比目标字母大的最小字母。
 * 数组里字母的顺序是循环的。举个例子，如果目标字母target = 'z' 并且有序数组为letters = ['a', 'b']，则答案返回'a'。
 * <p>
 * 示例:
 * <p>
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "a"
 * 输出: "c"
 * <p>
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "c"
 * 输出: "f"
 * <p>
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "d"
 * 输出: "f"
 * <p>
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "g"
 * 输出: "j"
 * <p>
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "j"
 * 输出: "c"
 * <p>
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "k"
 * 输出: "c"
 * <p>
 * 注:
 * letters长度范围在[2, 10000]区间内。
 * letters 仅由小写字母组成，最少包含两个不同的字母。
 * 目标字母target 是一个小写字母。
 */
public class Lc744 {

    public char nextGreatestLetter(char[] letters, char target) {
        if (letters[letters.length - 1] <= target || letters[0] > target) {
            return letters[0];
        }

        int i = 0, j = letters.length - 1, m;
        while (i < j) {
            m = i + (j - i) / 2;
            if (letters[m] <= target) {
                i = m + 1;
            } else {
                j = m;
            }
        }

        return letters[j];
    }
}
