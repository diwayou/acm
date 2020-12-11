package com.diwayou.acm.leetcode.lc700;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/daily-temperatures/
 * <p>
 * 根据每日 气温 列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用0 来代替。
 * 例如，给定一个列表temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，
 * 你的输出应该是[1, 1, 4, 2, 1, 1, 0, 0]。
 * 提示：气温 列表长度的范围是[1, 30000]。每个气温的值的均为华氏度，都是在[30, 100]范围内的整数。
 */
public class Lc739 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc739().dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
    }

    public int[] dailyTemperatures(int[] T) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

        for (int i = 1; i < T.length; i++) {
            if (T[i] > T[i - 1]) {
                T[i - 1] = 1;
            } else {
                queue.offer(new int[]{i - 1, T[i - 1]});
            }

            while (!queue.isEmpty()) {
                int[] top = queue.peek();
                if (T[i] > top[1]) {
                    T[top[0]] = i - top[0];
                    queue.remove();
                } else {
                    break;
                }
            }
        }

        while (!queue.isEmpty()) {
            T[queue.remove()[0]] = 0;
        }
        T[T.length - 1] = 0;

        return T;
    }

    // 这道题思维一定记住
    // 从后向前遍历
    // 跳跃判断
    public int[] dailyTemperatures1(int[] T) {
        int length = T.length;
        int[] res = new int[length];
        if (length == 1) {
            return res;
        }

        for (int i = length - 2; i >= 0; i--) {
            if (T[i + 1] > T[i]) {
                res[i]++;
            } else {
                int j = i + 1;
                while (T[j] <= T[i]) {
                    if (res[j] == 0) {//res[j]=0说明没有比当前数字更大的数字了
                        j = i;
                        break;
                    } else {
                        j = j + res[j];//找出比J大的数字的下标，跳跃检测
                    }
                }
                res[i] = j - i;
            }
        }

        return res;
    }
}
