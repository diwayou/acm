package com.diwayou.acm.leetcode.lc100;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode-cn.com/problems/min-stack/
 *
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x)-- 将元素 x 推入栈中。
 * pop()-- 删除栈顶的元素。
 * top()-- 获取栈顶元素。
 * getMin() -- 检索栈中的最小元素。
 * 示例:
 *
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 */
public class Lc155 {

    private static class MinStack {

        private Deque<Integer> stack;

        private Deque<Integer> minStack;

        /** initialize your data structure here. */
        public MinStack() {
            this.stack = new LinkedList<>();
            this.minStack = new LinkedList<>();
        }

        public void push(int x) {
            stack.offerLast(x);
            if (minStack.isEmpty()) {
                minStack.offerLast(x);
            } else if (x <= minStack.getLast()) {
                minStack.offerLast(x);
            }
        }

        public void pop() {
            int last = stack.removeLast();
            if (!minStack.isEmpty() && minStack.getLast() == last) {
                minStack.removeLast();
            }
        }

        public int top() {
            return stack.getLast();
        }

        public int getMin() {
            return minStack.getLast();
        }
    }
}
