package com.diwayou.acm.luogu.p27;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author gaopeng
 * @date 2020/9/27
 */
public class P2777 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        int num = cin.nextInt();
        int[] score = new int[num];
        for (int i = 0; i < num; i++) {
            score[i] = cin.nextInt();
        }

        Arrays.sort(score);

        int max = 0;
        for (int i = 0; i < num; i++) {
            max = Math.max(max, score[i] + num - i);
        }

        int ans = 0;
        for (int i = num - 1; i >= 0; i--) {
            if (score[i] + num >= max) {
                ans++;
            } else {
                break;
            }
        }

        System.out.println(ans);
    }
}
