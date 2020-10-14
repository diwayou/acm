package com.diwayou.acm.leetcode.lc0;

/**
 * https://leetcode-cn.com/problems/jump-game/
 *
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个位置。
 *
 * 示例1:
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 从位置 0 到 1 跳 1 步, 然后跳 3 步到达最后一个位置。
 *
 * 示例2:
 * 输入: [3,2,1,0,4]
 * 输出: false
 * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 */
public class Lc55 {

    public static void main(String[] args) {
        System.out.println(new Lc55().canJump(new int[]{2, 0}));
    }

    public boolean canJump(int[] nums) {
        if (nums.length < 2) {
            return true;
        }

        int curMax = nums[0];
        int preMax = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (i > curMax) {
                if (preMax < i) {
                    return false;
                }

                curMax = preMax;
            }

            if (preMax < nums[i] + i) {
                preMax = nums[i] + i;
                if (preMax >= nums.length - 1) {
                    return true;
                }
            }
        }

        return true;
    }

    public boolean canJump1(int[] nums) {
        //当前能跳到的最远处
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            //若当前i的位置比max还远，说明前面的点最大跳到max，跳不到i了，也就是在中间断了
            if (i > max) return false;
            //更新最远处
            max = Math.max(max, i + nums[i]);
        }

        return true;
    }
}
