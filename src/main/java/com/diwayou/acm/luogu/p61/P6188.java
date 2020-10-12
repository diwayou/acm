package com.diwayou.acm.luogu.p61;

import java.util.Scanner;

/**
 * @author gaopeng
 * @date 2020/9/30
 */
public class P6188 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        if (n == 0) {
            System.out.print("0 0 0");
            return;
        }

        for (int p = n / 14; p >= 0; p--) {
            for (int j = p; j <= n / 4; j++) {
                for (int k = p; k <= n / 3; k++) {
                    if (p * 7 + j * 4 + k * 3 == n) {
                        System.out.printf("%d %d %d", p, j, k);
                        return;
                    }
                }
            }
        }

        System.out.println(-1);
    }
}
