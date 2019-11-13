package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/count-primes/
 *
 * 统计所有小于非负整数 n 的质数的数量。
 *
 * 示例:
 * 输入: 10
 * 输出: 4
 * 解释: 小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
 */
public class Lc204 {

    public static void main(String[] args) {
        System.out.println(new Lc204().countPrimes1(100));
    }

    public int countPrimes(int n) {
        int re = 0;
        for (int i = 0; i < n; i++) {
            if (isPrime(i)) {
                re++;
            }
        }

        return re;
    }

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2 || n == 3) {
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

    public int countPrimes1(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        // 构建notPrime数组
        // Loop's ending condition is i * i < n instead of i < sqrt(n)
        // to avoid repeatedly calling an expensive function sqrt().
        for (int i = 2; i * i < n; i++) {
            if (notPrime[i]) {
                continue;
            }

            for (int j = i * i; j < n; j += i) {
                notPrime[j] = true;
            }
        }
        // 计数质数
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) count++;
        }

        return count;
    }
}
