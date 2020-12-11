package com.diwayou.acm.leetcode.lc1000;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/last-stone-weight/
 * <p>
 * 有一堆石头，每块石头的重量都是正整数。
 * 每一回合，从中选出两块最重的石头，然后将它们一起粉碎。假设石头的重量分别为x 和y，且x <= y。那么粉碎的可能结果如下：
 * 如果x == y，那么两块石头都会被完全粉碎；
 * 如果x != y，那么重量为x的石头将会完全粉碎，而重量为y的石头新重量为y-x。
 * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
 * <p>
 * 提示：
 * 1 <= stones.length <= 30
 * 1 <= stones[i] <= 1000
 */
public class Lc1046 {

    public int lastStoneWeight(int[] stones) {
        if (stones.length == 1) {
            return stones[0];
        }

        PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
        for (int s : stones) {
            q.add(s);
        }

        while (q.size() > 1) {
            int a = q.poll();
            int b = q.poll();
            q.add(a - b);
        }

        return q.peek();
    }

    public int lastStoneWeight1(int[] stones) {
        if (stones.length == 1) {
            return stones[0];
        }
        for (int x = 0; x < stones.length; x++) {
            Arrays.sort(stones);
            stones[stones.length - 1] = Math.abs(stones[stones.length - 1] - stones[stones.length - 2]);
            stones[stones.length - 2] = 0;
        }

        return stones[stones.length - 1];
    }
}
