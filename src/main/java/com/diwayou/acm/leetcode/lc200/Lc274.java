package com.diwayou.acm.leetcode.lc200;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/h-index/
 * <p>
 * 给定一位研究者论文被引用次数的数组（被引用次数是非负整数）。编写一个方法，计算出研究者的 h指数。
 * <p>
 * h 指数的定义: “h 代表“高引用次数”（high citations），一名科研人员的 h 指数是指他（她）的 （N 篇论文中）至多有 h 篇论文分别
 * 被引用了至少 h 次。（其余的N - h篇论文每篇被引用次数不多于 h 次。）”
 * <p>
 * 示例:
 * 输入: citations = [3,0,6,1,5]
 * 输出: 3
 * 解释: 给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
 * 由于研究者有 3 篇论文每篇至少被引用了 3 次，其余两篇论文每篇被引用不多于 3 次，所以她的 h 指数是 3。
 * <p>
 * 说明:如果 h 有多种可能的值，h 指数是其中最大的那个。
 */
public class Lc274 {

    public static void main(String[] args) {
        System.out.println(new Lc274().hIndex(new int[]{3, 0, 6, 1, 5}));
    }

    public int hIndex(int[] citations) {
        Arrays.sort(citations);

        int re = 0;
        for (int i = citations.length - 1; i >= 0; i--) {
            if (citations[i] > re) {
                re++;
            } else {
                break;
            }
        }

        return re;
    }

    public int hIndex2(int[] citations) {
        Arrays.sort(citations);

        int len = citations.length;
        // 特判
        if (len == 0 || citations[len - 1] == 0) {
            return 0;
        }
        int l = 0;
        int r = len - 1;
        while (l < r) {
            int mid = (l + r) >>> 1;
            // 比长度小，就得去掉该值
            if (citations[mid] < len - mid) {
                l = mid + 1;
            } else {
                // 比长度大是满足的，我们应该继续让 mid 往左走去尝试看有没有更小的 mid 值
                // 可以满足 mid 对应的值大于等于从 [mid, len - 1] 的长度
                r = mid;
            }
        }

        return len - l;
    }

    public int hIndex1(int[] citations) {
        int n = citations.length;
        int[] papers = new int[n + 1];
        // 计数
        for (int c : citations)
            papers[Math.min(n, c)]++;
        // 找出最大的 k
        int k = n;
        for (int s = papers[n]; k > s; s += papers[k])
            k--;
        return k;
    }
}
