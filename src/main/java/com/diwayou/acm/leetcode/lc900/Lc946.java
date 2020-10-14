package com.diwayou.acm.leetcode.lc900;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode-cn.com/problems/validate-stack-sequences/
 *
 * 给定pushed和popped两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false。
 *
 * 示例 1：
 * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * 输出：true
 * 解释：我们可以按以下顺序执行：
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 *
 * 示例 2：
 * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * 输出：false
 * 解释：1 不能在 2 之前弹出。
 * 
 * 提示：
 * 0 <= pushed.length == popped.length <= 1000
 * 0 <= pushed[i], popped[i] < 1000
 * pushed是popped的排列。
 */
public class Lc946 {

    public static void main(String[] args) {
        System.out.println(new Lc946().validateStackSequences(new int[]{1,0}, new int[]{1, 0}));
        System.out.println(new Lc946().validateStackSequences(new int[]{1,2,3,4,5}, new int[]{4,3,5,1,2}));
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed.length == 0) {
            return true;
        }

        int i = 0, j = 0;
        Deque<Integer> stack = new ArrayDeque<>(pushed.length);
        while (j < popped.length) {
            while (stack.isEmpty() || (i < pushed.length && stack.getLast() != popped[j])) {
                stack.offerLast(pushed[i++]);
            }

            if (stack.getLast() != popped[j]) {
                return false;
            }

            stack.removeLast();
            j++;
        }

        return j == popped.length;
    }
}
