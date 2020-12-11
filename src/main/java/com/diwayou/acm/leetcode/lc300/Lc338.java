package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/counting-bits/
 * <p>
 * 给定一个非负整数num。对于0 ≤ i ≤ num 范围中的每个数字i，计算其二进制数中的 1 的数目并将它们作为数组返回。
 * <p>
 * 示例 1:
 * 输入: 2
 * 输出: [0,1,1]
 * <p>
 * 示例2:
 * 输入: 5
 * 输出: [0,1,1,2,1,2]
 * <p>
 * 进阶:
 * 给出时间复杂度为O(n*sizeof(integer))的解答非常容易。但你可以在线性时间O(n)内用一趟扫描做到吗？
 * 要求算法的空间复杂度为O(n)。
 * 你能进一步完善解法吗？要求在C++或任何其他语言中不使用任何内置函数（如 C++ 中的__builtin_popcount）来执行此操作。
 */
public class Lc338 {

    public int[] countBits(int num) {
        int[] re = new int[num + 1];
        for (int i = 1; i <= num; ++i) {
            re[i] = re[i >> 1] + (i & 1); // x / 2 is x >> 1 and x % 2 is x & 1
        }

        return re;
    }

    public int[] countBits1(int num) {
        int[] re = new int[num + 1];
        for (int i = 1; i <= num; ++i) {
            re[i] = re[i & (i - 1)] + 1;
        }

        return re;
    }
}
