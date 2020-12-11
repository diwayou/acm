package com.diwayou.acm.leetcode.lc1000;

/**
 * https://leetcode-cn.com/problems/complement-of-base-10-integer/
 * <p>
 * 每个非负整数N都有其二进制表示。例如，5可以被表示为二进制"101"，11 可以用二进制"1011"表示，依此类推。注意，除N = 0外，任何二进制表示中都不含前导零。
 * 二进制的反码表示是将每个1改为0且每个0变为1。例如，二进制数"101"的二进制反码为"010"。
 * 给定十进制数N，返回其二进制表示的反码所对应的十进制整数。
 * <p>
 * 示例 1：
 * 输入：5
 * 输出：2
 * 解释：5 的二进制表示为 "101"，其二进制反码为 "010"，也就是十进制中的 2 。
 * <p>
 * 示例 2：
 * 输入：7
 * 输出：0
 * 解释：7 的二进制表示为 "111"，其二进制反码为 "000"，也就是十进制中的 0 。
 * <p>
 * 示例 3：
 * 输入：10
 * 输出：5
 * 解释：10 的二进制表示为 "1010"，其二进制反码为 "0101"，也就是十进制中的 5 。
 * <p>
 * 提示：
 * 0 <= N < 10^9
 */
public class Lc1009 {

    public static void main(String[] args) {
        System.out.println(new Lc1009().bitwiseComplement1((int) Math.pow(2, 16)));
    }

    public int bitwiseComplement(int N) {
        // 特殊情况 N == 0时
        if (N == 0) {
            return 1;
        }
        /*
         *  n的作用是得消除前导0
         *  例如 N 二进制位0000 1101时， n为 1111 0000
         *  然后二者取或运算，将N的前导00全部变为1，最后取反N就可以了
         */
        int n = N;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        n = ~n;
        N = n | N;

        return ~N;
    }

    public int bitwiseComplement1(int N) {
        if (N == 0)
            return 1;
        if (N == 1)
            return 0;
        int num = 1;
        while (num < N)
            num = (num << 1) + 1;

        return num ^ N;
    }
}
