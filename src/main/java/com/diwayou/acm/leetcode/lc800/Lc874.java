package com.diwayou.acm.leetcode.lc800;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/walking-robot-simulation/
 *
 * 机器人在一个无限大小的网格上行走，从点(0, 0) 处开始出发，面向北方。该机器人可以接收以下三种类型的命令：
 * -2：向左转90 度
 * -1：向右转 90 度
 * 1 <= x <= 9：向前移动x个单位长度
 * 在网格上有一些格子被视为障碍物。
 * 第 i个障碍物位于网格点 (obstacles[i][0], obstacles[i][1])
 * 如果机器人试图走到障碍物上方，那么它将停留在障碍物的前一个网格方块上，但仍然可以继续该路线的其余部分。
 * 返回从原点到机器人的最大欧式距离的平方。
 *
 * 示例 1：
 * 输入: commands = [4,-1,3], obstacles = []
 * 输出: 25
 * 解释: 机器人将会到达 (3, 4)
 *
 * 示例2：
 * 输入: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
 * 输出: 65
 * 解释: 机器人在左转走到 (1, 8) 之前将被困在 (1, 4) 处
 * 
 * 提示：
 * 0 <= commands.length <= 10000
 * 0 <= obstacles.length <= 10000
 * -30000 <= obstacle[i][0] <= 30000
 * -30000 <= obstacle[i][1] <= 30000
 * 答案保证小于2 ^ 31
 */
public class Lc874 {

    public static void main(String[] args) {
        System.out.println(new Lc874().robotSim(new int[]{4,-1,3}, new int[][]{}));
        System.out.println(new Lc874().robotSim(new int[]{4,-1,4,-2,4}, new int[][]{{2,4}}));
    }

    private static int[] xd = {0, 1, 0, -1};
    private static int[] yd = {1, 0, -1, 0};

    public int robotSim(int[] commands, int[][] obstacles) {
        Map<Integer, Set<Integer>> ob = new HashMap<>();
        for (int[] o : obstacles) {
            Set<Integer> s = ob.get(o[0]);
            if (s == null) {
                s = new HashSet<>();
                ob.put(o[0], s);
            }

            s.add(o[1]);
        }
        int x = 0, y = 0, idx = 0;
        Set<Integer> s;
        int re = 0;
        outer:
        for (int i = 0; i < commands.length; i++) {
            if (commands[i] == -1) {
                idx = (idx + 1) % 4;
            } else if (commands[i] == -2) {
                idx = (idx + 3) % 4;
            } else {
                for (int j = 0; j < commands[i]; j++) {
                    x += xd[idx];
                    y += yd[idx];
                    s = ob.get(x);
                    if (s != null && s.contains(y)) {
                        x -= xd[idx];
                        y -= yd[idx];
                        continue outer;
                    }

                    re = Math.max(re, x * x + y * y);
                }
            }
        }

        return re;
    }
}
