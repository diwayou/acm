package com.diwayou.acm.leetcode.lc1100;

/**
 * https://leetcode-cn.com/problems/prime-arrangements/
 * <p>
 * 请你帮忙给从 1 到 n的数设计排列方案，使得所有的「质数」都应该被放在「质数索引」（索引从 1 开始）上；你需要返回可能的方案总数。
 * 让我们一起来回顾一下「质数」：质数一定是大于 1 的，并且不能用两个小于它的正整数的乘积来表示。
 * 由于答案可能会很大，所以请你返回答案 模 mod10^9 + 7之后的结果即可。
 * <p>
 * 示例 1：
 * 输入：n = 5
 * 输出：12
 * 解释：举个例子，[1,2,5,4,3] 是一个有效的排列，但 [5,2,3,4,1] 不是，因为在第二种情况里质数 5 被错误地放在索引为 1 的位置上。
 * <p>
 * 示例 2：
 * 输入：n = 100
 * 输出：682289015
 * <p>
 * 提示：
 * 1 <= n <= 100
 */
public class Lc1175 {

    public static void main(String[] args) {
        System.out.println(new Lc1175().numPrimeArrangements(100));
    }

    public int numPrimeArrangements(int n) {
        int primeCnt = 0;
        for (int i = 1; i <= n; i++) {
            if (isPrime(i)) {
                primeCnt++;
            }
        }

        return (int) (factorial(primeCnt) * factorial(n - primeCnt) % 1000000007);
    }

    private static long factorial(int n) {
        long re = 1;
        for (int i = 2; i <= n; i++) {
            re *= i;
            re %= 1000000007;
        }

        return re;
    }

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }

        if (n == 2 || n == 3 || n == 5) {
            return true;
        }

        int sqrt = (int) Math.sqrt(n);
        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}
