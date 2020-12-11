package com.diwayou.acm.leetcode.lc200;

/**
 * https://leetcode-cn.com/problems/find-the-duplicate-number/
 * <p>
 * 给定一个包含n + 1 个整数的数组nums，其数字都在 1 到 n之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。
 * <p>
 * 示例 1:
 * 输入: [1,3,4,2,2]
 * 输出: 2
 * <p>
 * 示例 2:
 * 输入: [3,1,3,4,2]
 * 输出: 3
 * <p>
 * 说明：
 * 不能更改原数组（假设数组是只读的）。
 * 只能使用额外的 O(1) 的空间。
 * 时间复杂度小于 O(n^2) 。
 * 数组中只有一个重复的数字，但它可能不止重复出现一次。
 */
public class Lc287 {

    public static void main(String[] args) {
        System.out.println(new Lc287().findDuplicate(new int[]{1, 3, 4, 2, 2}));
        System.out.println(new Lc287().findDuplicate(new int[]{3, 1, 3, 4, 2}));
        System.out.println(new Lc287().findDuplicate(new int[]{2, 5, 9, 6, 9, 3, 8, 9, 7, 1}));
    }

    public int findDuplicate1(int[] nums) {
        int l = 1, r = nums.length - 1, mid, lcnt, rcnt, mcnt;
        while (l < r) {
            lcnt = rcnt = mcnt = 0;
            mid = (l + r) / 2;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] < mid) {
                    lcnt++;
                } else if (nums[i] > mid) {
                    rcnt++;
                } else {
                    mcnt++;
                }
            }

            if (mcnt > 1) {
                return mid;
            }

            if (lcnt >= mid) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }

    // 好精妙。。。
    public int findDuplicate(int[] nums) {
        int fast = 0, slow = 0;
        do {
            fast = nums[nums[fast]];
            slow = nums[slow];
        } while (fast != slow);

        slow = 0;
        while (slow != fast) {
            fast = nums[fast];
            slow = nums[slow];
        }

        return fast;
    }
}
