package com.diwayou.acm.team;

/**
 * https://leetcode-cn.com/problems/filling-bookcase-shelves/
 */
public class FillingBookcaseShelves {

    /**
     * 这题可以使用动态规划来做，使用dp[i]表示摆放前i本书需要的最小高度，先看一下LeetCode官网有人给出的动态图解，
     * 地址：https://leetcode-cn.com/problems/filling-bookcase-shelves/solution/dong-tai-gui-hua-python3-by-smoon1989/
     * 从动态演示的过程可以看出，求摆放前i本书需要的最小高度，首先需要求摆放前i-1书需要的最小高度，以此类推，最初需要计算的是摆放第0本书
     * 需要的最小高度，也就是0。根据前i-1本书求前i书需要的最小高度的思路是：尝试①将第i本书放在前i-1本书的下面以及②将前i-1本书的最后几本
     * 和第i本书放在同一层两种方案，看哪种方案高度更小就用哪种方案，依次求出摆放前1,…,n本书需要的最小高度。
     */
    public int minHeightShelves(int[][] books, int shelf_width) {
        int[] dp = new int[books.length + 1];

        dp[0] = 0;

        // 依次求摆放前i本书的最小高度
        for (int i = 1; i < dp.length; ++i) {
            int width = books[i - 1][0];
            int height = books[i - 1][1];
            dp[i] = dp[i - 1] + height;

            // 将前i - 1本书从第i - 1本开始放在与i同一层，知道这一层摆满或者所有的书都摆好
            for (int j = i - 1; j > 0 && width + books[j - 1][0] <= shelf_width; j--) {
                height = Math.max(height, books[j - 1][1]); // 每层的高度由最高的那本书决定
                width += books[j - 1][0];
                dp[i] = Math.min(dp[i], dp[j - 1] + height); // 选择高度最小的方法
            }
        }

        return dp[books.length];
    }
}
