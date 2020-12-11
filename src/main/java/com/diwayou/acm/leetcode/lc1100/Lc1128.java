package com.diwayou.acm.leetcode.lc1100;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/number-of-equivalent-domino-pairs/
 * <p>
 * 给你一个由一些多米诺骨牌组成的列表dominoes。
 * 如果其中某一张多米诺骨牌可以通过旋转 0度或 180 度得到另一张多米诺骨牌，我们就认为这两张牌是等价的。
 * 形式上，dominoes[i] = [a, b]和dominoes[j] = [c, d]等价的前提是a==c且b==d，或是a==d 且b==c。
 * 在0 <= i < j < dominoes.length的前提下，找出满足dominoes[i] 和dominoes[j]等价的骨牌对 (i, j) 的数量。
 * <p>
 * 示例：
 * 输入：dominoes = [[1,2],[2,1],[3,4],[5,6]]
 * 输出：1
 * <p>
 * 提示：
 * 1 <= dominoes.length <= 40000
 * 1 <= dominoes[i][j] <= 9
 */
public class Lc1128 {

    public static void main(String[] args) {
        System.out.println(new Lc1128().numEquivDominoPairs(new int[][]{{2, 2}, {1, 2}, {1, 2}, {1, 1}, {1, 2}, {1, 1}, {2, 2}}));
    }

    public int numEquivDominoPairs(int[][] dominoes) {
        Map<V, Integer> map = new HashMap<>();

        int re = 0;
        for (int[] d : dominoes) {
            V v = new V(d);
            Integer c = map.get(v);
            if (c == null) {
                map.put(v, 1);
            } else {
                re += c;
                map.put(v, c + 1);
            }
        }

        return re;
    }

    private class V {
        int[] d;

        public V(int[] d) {
            this.d = d;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            V v = (V) o;
            return (d[0] == v.d[0] && d[1] == v.d[1]) || (d[0] == v.d[1] && d[1] == v.d[0]);
        }

        @Override
        public int hashCode() {
            return d[0] + d[1];
        }
    }

    public int numEquivDominoPairs1(int[][] dominoes) {
        int res = 0;
        int[][] arrays = new int[10][10];
        for (int[] domino : dominoes) {
            arrays[domino[0]][domino[1]]++;
        }
        for (int i = 1; i < 10; i++) {
            for (int j = i; j < 10; j++) {
                if (i == j) {
                    res += arrays[i][j] * (arrays[i][j] - 1) / 2;
                } else {
                    int num = arrays[i][j] + arrays[j][i];
                    res += num * (num - 1) / 2;
                }
            }
        }

        return res;
    }
}
