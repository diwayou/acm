package com.diwayou.acm.leetcode.lc700;

/**
 * https://leetcode-cn.com/problems/couples-holding-hands/
 * <p>
 * N 对情侣坐在连续排列的 2N 个座位上，想要牵到对方的手。 计算最少交换座位的次数，以便每对情侣可以并肩坐在一起。 一次交换可选择任意两人，让他们站起来交换座位。
 * 人和座位用0到2N-1的整数表示，情侣们按顺序编号，第一对是(0, 1)，第二对是(2, 3)，以此类推，最后一对是(2N-2, 2N-1)。
 * 这些情侣的初始座位row[i]是由最初始坐在第 i 个座位上的人决定的。
 * <p>
 * 示例 1:
 * 输入: row = [0, 2, 1, 3]
 * 输出: 1
 * 解释: 我们只需要交换row[1]和row[2]的位置即可。
 * <p>
 * 示例 2:
 * 输入: row = [3, 2, 0, 1]
 * 输出: 0
 * 解释: 无需交换座位，所有的情侣都已经可以手牵手了。
 * <p>
 * 说明:
 * len(row) 是偶数且数值在[4, 60]范围内。
 * 可以保证row 是序列0...len(row)-1的一个全排列。
 */
public class Lc765 {

    public int minSwapsCouples(int[] row) {
        int res = 0;
        int[] memo = new int[row.length];

        for (int i = 0; i < row.length; i++) {
            memo[row[i]] = i;
        }

        int next; // 所期望的情侣
        for (int i = 0; i < row.length; i += 2) {
            next = ((row[i] & 1) != 0) ? row[i] - 1 : row[i] + 1;
            if (row[i + 1] == next) {
                continue;
            }

            swap(row, i + 1, memo[next]);
            swap(memo, next, row[memo[next]]);
            res++;
        }

        return res;
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    /**
     * 我们设想一下加入有两对情侣互相坐错了位置，我们至多只需要换一次。
     * 如果三对情侣相互坐错了位置，A1+B2,B1+C2,C1+A2。他们三个之间形成了一个环，我们只需要交换两次。
     * 如果四队情侣相互坐错了位置，即这四对情侣不与其他情侣坐在一起，A1+B2,B1+C2,C1+D2,D1+A2.他们四个之间形成了一个环，他们只需要交换
     * 三次并且不用和其他情侣交换，就可以将这四对情侣交换好，
     * 以此类推，其实就是假设k对情侣形成一个环状的错误链，我们只需要交换k - 1次就可以将这k对情侣的位置排好。
     * 所以问题转化成N对情侣中，有几个这样的错误环。
     * 我们可以使用并查集来处理，每次遍历相邻的两个位置，如果他们本来就是情侣，他们处于大小为1的错误环中，只需要交换0次。如果不是情侣，
     * 说明他们呢两对处在同一个错误环中，我们将他们合并（union），将所有的错坐情侣合并和后，答案就是情侣对 - 环个数。
     * 这也说明，最差的情况就是所有N对情侣都在一个环中，这时候我们需要N - 1调换。
     * 最好情况每对情侣已经坐好了，已经有N个大小为1的环，这时候我们需要N - N次调换。
     */
    private int[] id;
    private int[] sz;

    public int minSwapsCouples1(int[] row) {
        int n = row.length, m = n / 2, circle = 0;
        id = new int[m];
        sz = new int[m];
        for (int i = 0; i < m; i++) {
            id[i] = i;
            sz[i] = 1;
        }
        for (int i = 0; i < n; i += 2) {
            union(row[i] / 2, row[i + 1] / 2);
        }
        for (int i = 0; i < m; i++) {
            if (i == find(i)) {
                circle++;
            }
        }

        return m - circle;
    }

    private int find(int i) {
        while (i != id[i]) {
            i = id[i];
        }

        return i;
    }

    private void union(int i, int j) {
        i = find(i);
        j = find(j);
        if (i == j) {
            return;
        }

        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
}
