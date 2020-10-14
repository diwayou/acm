package com.diwayou.acm.leetcode.lc1000;

/**
 * https://leetcode-cn.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
 *
 * 在歌曲列表中，第 i 首歌曲的持续时间为 time[i] 秒。
 * 返回其总持续时间（以秒为单位）可被 60 整除的歌曲对的数量。形式上，我们希望索引的数字i < j 且有(time[i] + time[j]) % 60 == 0。
 *
 * 示例 1：
 * 输入：[30,20,150,100,40]
 * 输出：3
 * 解释：这三对的总持续时间可被 60 整数：
 * (time[0] = 30, time[2] = 150): 总持续时间 180
 * (time[1] = 20, time[3] = 100): 总持续时间 120
 * (time[1] = 20, time[4] = 40): 总持续时间 60
 *
 * 示例 2：
 * 输入：[60,60,60]
 * 输出：3
 * 解释：所有三对的总持续时间都是 120，可以被 60 整数。
 * 
 * 提示：
 * 1 <= time.length <= 60000
 * 1 <= time[i] <= 500
 */
public class Lc1010 {

    public static void main(String[] args) {
        System.out.println(new Lc1010().numPairsDivisibleBy60(new int[]{60, 60, 60}));
    }

    public int numPairsDivisibleBy60(int[] time) {
        int[] cnt = new int[501];
        for (int t : time) {
            cnt[t]++;
        }

        int re = 0;
        for (int i = 0; i < time.length; i++) {
            int t = time[i];
            cnt[t]--;

            for (int j = 60; j < 1000; j += 60) {
                int d = j - t;
                if (d > 0 && d <= 500 && cnt[d] > 0) {
                    re += cnt[d];
                }
            }
        }

        return re;
    }

    public int numPairsDivisibleBy601(int[] time) {
        int[] count = new int[60];
        for (int t : time) {
            count[t % 60]++;
        }
        int num = (count[0] * (count[0] - 1)) / 2;
        num += (count[30] * (count[30] - 1)) / 2;
        for (int i = 1; i < 30; i++) {
            num += count[i] * count[60 - i];
        }

        return num;
    }

    public int numPairsDivisibleBy602(int[] time) {
        int count = 0;
        int[] index = new int[60];
        for (int n : time) {
            // 最后还要取模60，是因为(60 - n % 60)的值有可能等于60，而对于我们声明的数组来说，60已经越界了
            count += index[(60 - n % 60) % 60];
            index[n % 60]++;
        }
        return count;
    }
}
