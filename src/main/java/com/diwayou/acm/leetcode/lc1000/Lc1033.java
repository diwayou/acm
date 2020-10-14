package com.diwayou.acm.leetcode.lc1000;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/moving-stones-until-consecutive/
 *
 * Three stones are on a number line at positions a, b, and c.
 *
 * Each turn, you pick up a stone at an endpoint (ie., either the lowest or highest position stone),
 * and move it to an unoccupied position between thoseendpoints. Formally, let's say the stones are currently
 * at positions x, y, z with x < y < z. You pick up the stone at either position x or position z, and move that stone
 * to an integer position k, with x < k < z and k != y.
 *
 * The game ends when you cannot make any more moves, ie. the stones are in consecutive positions.
 *
 * When the game ends, what is the minimum and maximum number of moves that you could have made? Return the answer as
 * an length 2 array: answer = [minimum_moves, maximum_moves]
 */
public class Lc1033 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc1033().numMovesStones(3, 1, 5)));
    }

    public int[] numMovesStones(int a, int b, int c) {
        int x, y, z;

        if (a > b) {
            x = a;
            z = b;
        } else {
            x = b;
            z = a;
        }
        if (c > x) {
            y = x;
            x = c;
        } else if (c < z) {
            y = z;
            z = c;
        } else {
            y = c;
        }

        int min;
        int diff1 = x - y;
        int diff2 = y - z;
        if (diff1 == 1 && diff2 == 1) {
            min = 0;
        } else if (diff1 <= 2 || diff2 <= 2) {
            min = 1;
        } else {
            min = 2;
        }

        int max = (diff1 == 1 ? 0 : diff1 - 1) + (diff2 == 1 ? 0 : diff2 - 1);
        //max = x - z - 2;

        return new int[]{min, max};
    }
}
