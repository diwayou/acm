package com.diwayou.acm.team;

/**
 * https://leetcode-cn.com/problems/new-21-game/submissions/
 */
public class New21Game {
    public double new21Game(int N, int K, int W) {
        if (K - 1 + W <= N || K == 0)
            return 1.0;
        double[] p = new double[N + 1];
        double sum = 0.0;
        double q = 1.0 / W;
        double ans = 0.0;
        for (int i = 1; i <= N; ++i) {
            if (i <= W) {
                p[i] = q;
            }
            p[i] += q * sum;
            if (i < K) {
                sum += p[i];
            } else {
                ans += p[i];
            }
            if (i > W) {
                sum -= p[i - W];
            }
        }

        return ans;
    }

    public double new21Game1(int N, int K, int W) {
        if (K == 0) {
            return 1;
        }

        double[] dp = new double[N + 1];
        dp[0] = 1.0;
        double wSum = 1.0;
        int iw;
        for (int i = 1; i < N + 1; ++i) {
            dp[i] = wSum / W;
            if (i < K) {
                wSum += dp[i];
            }

            iw = i - W;
            if (iw >= 0 && iw < K) {
                wSum -= dp[iw];
            }
        }

        double result = 0.0;
        for (int i = K; i < N + 1; ++i) {
            result += dp[i];
        }

        return result;
    }

    public double new21Game2(int N, int K, int W) {
        if (K == 0 || N >= K + W) return 1;
        double dp[] = new double[N + 1], Wsum = 1, res = 0;
        dp[0] = 1;
        for (int i = 1; i <= N; i++) {
            dp[i] = Wsum / W;
            if (i < K) Wsum += dp[i];
            else res += dp[i];
            if (i - W >= 0) Wsum -= dp[i - W];
        }
        return res;
    }
}