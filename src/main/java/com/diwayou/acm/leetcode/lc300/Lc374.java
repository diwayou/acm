package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/guess-number-higher-or-lower/
 * <p>
 * 我们正在玩一个猜数字游戏。 游戏规则如下：
 * 我从1到n选择一个数字。 你需要猜我选择了哪个数字。
 * 每次你猜错了，我会告诉你这个数字是大了还是小了。
 * 你调用一个预先定义好的接口guess(int num)，它会返回 3 个可能的结果（-1，1或 0）：
 * -1 : 我的数字比较小
 * 1 : 我的数字比较大
 * 0 : 恭喜！你猜对了！
 * <p>
 * 示例 :
 * 输入: n = 10, pick = 6
 * 输出: 6
 */
public class Lc374 {

    public int guessNumber(int n) {
        int low = 1;
        int high = n;
        while (low <= high) {
            // 也可以(low + high) / 2，但是这种方式有可能加法溢出，所以建议用下边这种方式
            int mid = low + (high - low) / 2;
            int res = guess(mid);
            if (res == 0)
                return mid;
            else if (res < 0)
                high = mid - 1;
            else
                low = mid + 1;
        }

        return -1;
    }

    private int guess(int mid) {
        return 0;
    }
}
