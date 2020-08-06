package com.diwayou.acm.leetcode.lc800;

/**
 * https://leetcode-cn.com/problems/most-profit-assigning-work/
 *
 * 有一些工作：difficulty[i] 表示第i个工作的难度，profit[i]表示第i个工作的收益。
 * 现在我们有一些工人。worker[i]是第i个工人的能力，即该工人只能完成难度小于等于worker[i]的工作。
 * 每一个工人都最多只能安排一个工作，但是一个工作可以完成多次。
 *
 * 举个例子，如果3个工人都尝试完成一份报酬为1的同样工作，那么总收益为 $3。如果一个工人不能完成任何工作，他的收益为 $0 。
 * 我们能得到的最大收益是多少？
 *
 * 示例：
 * 输入: difficulty = [2,4,6,8,10], profit = [10,20,30,40,50], worker = [4,5,6,7]
 * 输出: 100
 * 解释: 工人被分配的工作难度是 [4,4,6,6] ，分别获得 [20,20,30,30] 的收益。
 *
 * 提示:
 * 1 <= difficulty.length = profit.length <= 10000
 * 1 <= worker.length <= 10000
 * difficulty[i], profit[i], worker[i]  的范围是 [1, 10^5]
 */
public class Lc826 {

    public static void main(String[] args) {
        System.out.println(new Lc826().maxProfitAssignment(new int[]{2,4,6,8,10}, new int[]{10,20,30,40,50}, new int[]{4,5,6,7}));
    }

    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int[] maxDiffPro = new int[100001];
        for (int i = 0; i < difficulty.length; i++) {
            if (profit[i] > maxDiffPro[difficulty[i]]) {
                maxDiffPro[difficulty[i]] = profit[i];
            }
        }

        int curMax = 0;
        for (int i = 0; i < maxDiffPro.length; i++) {
            if (maxDiffPro[i] > curMax) {
                curMax = maxDiffPro[i];
            } else {
                maxDiffPro[i] = curMax;
            }
        }

        int re = 0;
        for (int d : worker) {
            re += maxDiffPro[d];
        }

        return re;
    }
}