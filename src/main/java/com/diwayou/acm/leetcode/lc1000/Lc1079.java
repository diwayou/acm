package com.diwayou.acm.leetcode.lc1000;

/**
 * https://leetcode-cn.com/problems/letter-tile-possibilities/
 * <p>
 * 你有一套活字字模tiles，其中每个字模上都刻有一个字母tiles[i]。返回你可以印出的非空字母序列的数目。
 * <p>
 * 注意：本题中，每个活字字模只能使用一次。
 * <p>
 * 示例 1：
 * 输入："AAB"
 * 输出：8
 * 解释：可能的序列为 "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA"。
 * <p>
 * 示例 2：
 * 输入："AAABBC"
 * 输出：188
 * <p>
 * 提示：
 * 1 <= tiles.length <= 7
 * tiles 由大写英文字母组成
 *
 * @author gaopeng
 * @date 2020/10/14
 */
public class Lc1079 {

    private int re;

    public int numTilePossibilities(String tiles) {
        char[] t = tiles.toCharArray();

        calc(t, 0);

        return re - 1;
    }

    private void calc(char[] t, int i) {
        re += 1;

        outer:
        for (int k = i; k < t.length; k++) {
            for (int j = i; j < k; j++) {
                if (t[j] == t[k]) {
                    continue outer;
                }
            }

            swap(t, i, k);
            calc(t, i + 1);
            swap(t, i, k);
        }
    }

    private static void swap(char[] nums, int i, int j) {
        char t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public static void main(String[] args) {
        System.out.println(new Lc1079().numTilePossibilities("AAABBC"));
    }
}
