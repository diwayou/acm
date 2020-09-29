package com.diwayou.acm.luogu.p10;

import java.util.Scanner;

/**
 * @author gaopeng
 * @date 2020/9/27
 */
public class P1076 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        int n = cin.nextInt();
        int m = cin.nextInt();
        boolean[][] towerHas = new boolean[n][m];
        int[][] towerStep = new int[n][m];
        int[] upCnt = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                towerHas[i][j] = cin.nextInt() == 1;
                towerStep[i][j] = cin.nextInt();

                if (towerHas[i][j]) {
                    upCnt[i]++;
                }
            }
        }
        int first = cin.nextInt();

        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += towerStep[i][first];

            int x = towerStep[i][first];
            x = (x - 1) % upCnt[i] + 1;

            while (x > 0) {
                if (towerHas[i][first]) {
                    x--;
                }
                if (x == 0) {
                    break;
                }

                first++;
                if (first == m) {
                    first = 0;
                }
            }
        }

        System.out.println(ans % 20123);
    }
}
