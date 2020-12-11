package com.diwayou.acm.leetcode.lc500;

/**
 * https://leetcode-cn.com/problems/perfect-number/
 * <p>
 * 对于一个正整数，如果它和除了它自身以外的所有正因子之和相等，我们称它为“完美数”。
 * 给定一个整数n，如果他是完美数，返回True，否则返回False
 * <p>
 * 示例：
 * 输入: 28
 * 输出: True
 * 解释: 28 = 1 + 2 + 4 + 7 + 14
 * <p>
 * 提示：
 * 输入的数字n 不会超过 100,000,000. (1e8)
 */
public class Lc507 {

    public static void main(String[] args) {
        System.out.println(new Lc507().checkPerfectNumber(29));
    }

    public boolean checkPerfectNumber(int num) {
        if (num == 0) {
            return false;
        }

        int re = 0;
        for (int i = num / 2; i > 0; i--) {
            if (num % i == 0) {
                re += i;
            }
        }

        return re == num;
    }

    public boolean checkPerfectNumber1(int num) {
        switch (num) {
            case 6:
            case 28:
            case 496:
            case 8128:
            case 33550336:
                return true;
        }
        return false;
    }

    public boolean checkPerfectNumber2(int num) {
        if (num <= 1) {
            return false;
        }
        int sum = 1;
        int i = 2;
        int sqrt = (int) Math.sqrt(num);
        for (; i <= sqrt; i++) {
            if (num % i == 0) {
                sum += i;
                sum += num / i;
            }
        }

        return sum == num;
    }
}
