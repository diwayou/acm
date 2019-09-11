package com.diwayou.team.carpooling;

import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode-cn.com/problems/car-pooling/
 */
public class CarPooling2 {
    public static void main(String[] args) {
        int[][] trips = {{9, 3, 6}, {8, 1, 7}, {6, 6, 8}, {8, 4, 9}, {4, 2, 9}};
        int cap = 28;
        System.out.println(new CarPooling2().carPooling(trips, cap));
    }

    public boolean carPooling(int[][] trips, int capacity) {
        Map<Integer, Integer> t = new TreeMap<>();
        for (int[] trip : trips) {
            t.put(trip[1], t.getOrDefault(trip[1], 0) - trip[0]);
            t.put(trip[2], t.getOrDefault(trip[2], 0) + trip[0]);
        }

        for (Map.Entry<Integer, Integer> entry : t.entrySet()) {
            if ((capacity += entry.getValue()) < 0) {
                return false;
            }
        }

        return true;
    }
}
