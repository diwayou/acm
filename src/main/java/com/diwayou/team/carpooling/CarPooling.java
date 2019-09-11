package com.diwayou.team.carpooling;

/**
 * https://leetcode-cn.com/problems/car-pooling/
 */
public class CarPooling {
    public static void main(String[] args) {
        int[][] trips = {{9, 3, 6}, {8, 1, 7}, {6, 6, 8}, {8, 4, 9}, {4, 2, 9}};
        int cap = 28;
        System.out.println(new CarPooling().carPooling(trips, cap));
    }

    public boolean carPooling(int[][] trips, int capacity) {
        int[] d = new int[1001];
        for (int[] trip : trips) {
            d[trip[1]] -= trip[0];
            d[trip[2]] += trip[0];
        }
        for (int c : d) {
            if ((capacity += c) < 0) {
                return false;
            }
        }
        return true;
    }
}
