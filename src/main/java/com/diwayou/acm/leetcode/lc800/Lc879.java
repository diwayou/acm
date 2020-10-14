package com.diwayou.acm.leetcode.lc800;

import java.util.ArrayList;

/**
 * https://leetcode-cn.com/problems/profitable-schemes/
 *
 * 帮派里有 G 名成员，他们可能犯下各种各样的罪行。
 * 第i种犯罪会产生profit[i]的利润，它要求group[i]名成员共同参与。
 * 让我们把这些犯罪的任何子集称为盈利计划，该计划至少产生P 的利润。
 * 有多少种方案可以选择？因为答案很大，所以返回它模10^9 + 7的值。
 *
 * 示例1：
 * 输入：G = 5, P = 3, group = [2,2], profit = [2,3]
 * 输出：2
 * 解释：
 * 至少产生 3 的利润，该帮派可以犯下罪 0 和罪 1 ，或仅犯下罪 1 。
 * 总的来说，有两种方案。
 *
 * 示例2:
 * 输入：G = 10, P = 5, group = [2,3,5], profit = [6,7,8]
 * 输出：7
 * 解释：
 * 至少产生 5 的利润，只要他们犯其中一种罪就行，所以该帮派可以犯下任何罪行 。
 * 有 7 种可能的计划：(0)，(1)，(2)，(0,1)，(0,2)，(1,2)，以及 (0,1,2) 。
 *
 *
 * 提示：
 * 1 <= G <= 100
 * 0 <= P <= 100
 * 1 <= group[i] <= 100
 * 0 <= profit[i] <= 100
 * 1 <= group.length = profit.length <= 100
 */
public class Lc879 {

    public static void main(String[] args) {
        System.out.println(new Lc879().profitableSchemes1(100, 5, new int[]{2, 3, 5}, new int[]{6, 7, 8}));
    }

    private int g;
    private int p;
    private int[] group;
    private int[] profit;
    private int n;
    private static final int mod = 1000000007;

    public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        this.g = G;
        this.p = P;
        this.group = group;
        this.profit = profit;
        this.n = group.length;

        return profitableSchemesHelper(0, new ArrayList<>(g), 0, 0);
    }

    private int profitableSchemesHelper(int i, ArrayList<Integer> backtrack, int cnt, int cur) {
        int re = 0;
        if (cur >= p) {
            re++;
        }
        if (i == n) {
            return re;
        }

        for (int j = i; j < n; j++) {
            if (cnt + group[j] <= g) {
                backtrack.add(j);
                re += profitableSchemesHelper(j + 1, backtrack, cnt + group[j], cur + profit[j]);

                re %= mod;
                backtrack.remove(backtrack.size() - 1);
            }
        }

        return re % mod;
    }

    /*
        如果该计划的利润 ≥P 的话，我们无需考虑利润问题，因为它肯定超过盈利所需的阀值。类似地，如果该计划需要的人数 >G 的话，
        我们无需考虑该计划，因为它对于这个帮派来说太大了。
        因此，边界足够小，可以使用动态规划。让我们跟踪 cur[p][g]，它包括有盈利能力的计划数量 pp 以及要求参与的团伙成员数量 g: 除了我们
        会称（不改变答案）所有计划至少获利为 P 美元而不是确切地 P 美元。
        算法
        跟踪如上所定义的 cur[p][g]，让我们了解它是如何变化的，当我们考虑一个额外的犯罪，获利 p0 且需要 g0 名成员。我们将更新后的数目放入 cur2。
        对于每个利润 p1、需要 g1 的计划，该计划加上我们考虑的额外犯罪 (p0, g0) ，其利润为 p2 = min(p1 + p0, P)，需要 g2 = g1 + g0 人。
    */
    public int profitableSchemes1(int G, int P, int[] group, int[] profit) {
        int mod = 1_000_000_007;
        int n = group.length;
        long[][][] dp = new long[2][P+1][G+1];
        dp[0][0][0] = 1;

        for (int i = 0; i < n; ++i) {
            int p0 = profit[i];  // the current crime profit
            int g0 = group[i];  // the current crime group size

            long[][] cur = dp[i % 2];
            long[][] cur2 = dp[(i + 1) % 2];

            // Deep copy cur into cur2
            for (int j = 0; j <= P; ++j)
                for (int k = 0; k <= G; ++k)
                    cur2[j][k] = cur[j][k];

            for (int p1 = 0; p1 <= P; ++p1) {  // p1 : the current profit
                // p2 : the new profit after committing this crime
                int p2 = Math.min(p1 + p0, P);
                for (int g1 = 0; g1 <= G - g0; ++g1) {  // g1 : the current group size
                    // g2 : the new group size after committing this crime
                    int g2 = g1 + g0;
                    cur2[p2][g2] += cur[p1][g1];
                    cur2[p2][g2] %= mod;
                }
            }
        }

        // Sum all schemes with profit P and group size 0 <= g <= G.
        long ans = 0;
        for (long x : dp[n%2][P])
            ans = (ans + x) % mod;

        return (int) ans % mod;
    }

    public int profitableSchemes2(int G, int P, int[] group, int[] profit) {
        int n = group.length;
        int[][] dp = new int[G + 1][P + 1];
        final int MOD = (int) 1e9 + 7;
        dp[0][0] = 1;
        for (int k = 1; k <= n; k++) {
            int g = group[k - 1], p = profit[k - 1];
            for (int i = G; i >= g; i--) {
                for (int j = P; j >= 0; j--) {
                    dp[i][j] = (dp[i][j] + dp[i - g][Math.max(0, j - p)]) % MOD;
                }
            }
        }
        int result = 0;
        for (int i = 0; i <= G; i++) {
            result = (result + dp[i][P]) % MOD;
        }

        return result;
    }
}
