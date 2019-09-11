package com.diwayou.team.carpooling;

import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/car-pooling/
 */
public class CarPooling1 {
    public static void main(String[] args) {
        int[][] trips = {{9, 3, 6}, {8, 1, 7}, {6, 6, 8}, {8, 4, 9}, {4, 2, 9}};
        int cap = 28;
        System.out.println(new CarPooling1().carPooling(trips, cap));
    }

    public boolean carPooling(int[][] trips, int capacity) {
        PriorityQueue<Integer> q = new PriorityQueue<>(trips.length * 2);
        for (int[] trip : trips) {
            int pick_up = (trip[1] << 10) | (1 << 9) | trip[0];
            int drop_off = (trip[2] << 10) | trip[0];
            q.offer(pick_up);
            q.offer(drop_off);
        }
        while (!q.isEmpty()) {
            int key = q.poll();
            int sign = ((key >> 9) & 1) == 1 ? 1 : -1;
            int num = key & 0xFF;
            if ((capacity -= sign * num) < 0)
                return false;
        }
        return true;
    }
}
