package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/friends-of-appropriate-ages/
 *
 * 人们会互相发送好友请求，现在给定一个包含有他们年龄的数组，ages[i] 表示第 i 个人的年龄。
 * 当满足以下条件时，A 不能给 B（A、B不为同一人）发送好友请求：
 * age[B] <= 0.5 * age[A] + 7
 * age[B] > age[A]
 * age[B] > 100 && age[A] < 100
 * 否则，A 可以给 B 发送好友请求。
 * 注意如果 A 向 B 发出了请求，不等于 B 也一定会向 A 发出请求。而且，人们不会给自己发送好友请求。 
 * 求总共会发出多少份好友请求?
 *
 * 示例 1:
 * 输入: [16,16]
 * 输出: 2
 * 解释: 二人可以互发好友申请。
 *
 * 示例 2:
 * 输入: [16,17,18]
 * 输出: 2
 * 解释: 好友请求可产生于 17 -> 16, 18 -> 17.
 *
 * 示例 3:
 * 输入: [20,30,100,110,120]
 * 输出: 3
 * 解释: 好友请求可产生于 110 -> 100, 120 -> 110, 120 -> 100.
 *  
 * 说明:
 * 1 <= ages.length <= 20000.
 * 1 <= ages[i] <= 120.
 */
public class Lc825 {

    public static void main(String[] args) {
        System.out.println(new Lc825().numFriendRequests(new int[]{73,106,39,6,26,15,30,100,71,35,46,112,6,60,110}));
    }

    public int numFriendRequests2(int[] ages) {
        // 年龄的范围是 1-120
        int[] array = new int[121];
        for (int age : ages) {
            array[age]++;
        }

        int result = 0;
        for (int ageA = 0; ageA <= 120; ageA++) {
            // 统计满足年龄的人数
            int count = 0;
            // 在满足条件的最小年龄和最大年龄间遍历
            int minAgeB = ageA / 2 + 7 + 1;
            for (int ageB = minAgeB; ageB < ageA; ageB++) {
                count += array[ageB];
            }

            count *= array[ageA];
            // 加上年龄一样大的人数
            if (array[ageA] > 1 && ageA >= minAgeB) {
                count += array[ageA] * (array[ageA] - 1);
            }
            result += count;
        }
        return result;
    }

    public int numFriendRequests1(int[] ages) {
        int[] count = new int[121];
        for (int age : ages) count[age]++;

        int ans = 0;
        for (int ageA = 0; ageA <= 120; ageA++) {
            int countA = count[ageA];
            for (int ageB = 0; ageB <= 120; ageB++) {
                int countB = count[ageB];
                if (ageA * 0.5 + 7 >= ageB) continue;
                if (ageA < ageB) continue;
                if (ageA < 100 && 100 < ageB) continue;
                ans += countA * countB;
                if (ageA == ageB) ans -= countA;
            }
        }

        return ans;
    }

    public int numFriendRequests(int[] ages) {
        int re = 0;
        for (int i = 0; i < ages.length; i++) {
            for (int j = i + 1; j < ages.length; j++) {
                re += numRequest(ages, i, j);
            }
        }

        return re;
    }

    public static int numRequest(int[] ages, int a, int b) {
        if (ages[a] == ages[b]) {
            if (ages[a] <= 0.5 * ages[b] + 7) {
                return 0;
            }

            print("equal", ages, a, b);
            return 2;
        }

        if (ages[a] < ages[b]) {
            if (ages[a] <= 0.5 * ages[b] + 7) {
                return 0;
            }

            print("one", ages, b, a);
            return 1;
        }

        if (ages[b] <= 0.5 * ages[a] + 7) {
            return 0;
        }

        print("two", ages, a, b);
        return 1;
    }

    private static void print(String comment, int[] ages, int a, int b) {
        //System.out.println(String.format("%s: %d->%d, %b", comment, ages[a], ages[b], isRequest(ages, a, b)));
    }

    public static boolean isRequest(int[] ages, int a, int b) {
        if (ages[a] < ages[b]) {
            return false;
        }

        if (ages[a] < 100 && ages[b] > 100) {
            return false;
        }

        if (ages[b] <= 0.5 * ages[a] + 7) {
            return false;
        }

        return true;
    }
}
