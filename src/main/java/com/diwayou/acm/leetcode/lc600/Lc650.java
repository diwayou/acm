package com.diwayou.acm.leetcode.lc600;

/**
 * https://leetcode-cn.com/problems/2-keys-keyboard/
 * <p>
 * 最初在一个记事本上只有一个字符 'A'。你每次可以对这个记事本进行两种操作：
 * Copy All (复制全部) : 你可以复制这个记事本中的所有字符(部分的复制是不允许的)。
 * Paste (粘贴) : 你可以粘贴你上一次复制的字符。
 * 给定一个数字n。你需要使用最少的操作次数，在记事本中打印出恰好n个 'A'。输出能够打印出n个 'A' 的最少操作次数。
 */
public class Lc650 {

    public static void main(String[] args) {
        System.out.println(new Lc650().minSteps2(9));
    }

    public int minSteps(int n) {
        if (n <= 1) {
            return 0;
        }

        if (n <= 5) {
            return n;
        }

        int sqrt = (int) Math.sqrt(n);
        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0) {
                return i + minSteps(n / i);
            }
        }

        return n;
    }

    public int minSteps1(int n) {
        if (n <= 1) {
            return 0;
        }

        if (n <= 5) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i <= 5; i++) {
            dp[i] = i;
        }

        int j;
        for (int i = 6; i <= n; i++) {
            int sqrt = (int) Math.sqrt(i);
            for (j = 2; j <= sqrt; j++) {
                if (i % j == 0) {
                    dp[i] = j + dp[i / j];
                    break;
                }
            }

            if (j > sqrt) {
                dp[i] = i;
            }
        }

        return dp[n];
    }

    public int minSteps2(int n) {
        if (n == 1) {
            return 0;
        }

        if (n <= 5) {
            return n;
        }

        int curLength = 1, steps = 1, cpLength = 1;
        int cpFlag = 1;

        while (n != curLength) {
            if ((n - curLength) % curLength == 0 && cpFlag == 0) {
                cpLength = curLength;
                cpFlag = 1;
            } else {
                curLength += cpLength;
                cpFlag = 0;
            }
            steps++;
        }

        return steps;
    }
}
