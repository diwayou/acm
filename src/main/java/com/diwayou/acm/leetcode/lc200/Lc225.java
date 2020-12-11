package com.diwayou.acm.leetcode.lc200;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode-cn.com/problems/implement-stack-using-queues/
 * <p>
 * 使用队列实现栈的下列操作：
 * push(x) -- 元素 x 入栈
 * pop() -- 移除栈顶元素
 * top() -- 获取栈顶元素
 * empty() -- 返回栈是否为空
 * <p>
 * 注意:
 * 你只能使用队列的基本操作-- 也就是push to back, peek/pop from front, size, 和is empty这些操作是合法的。
 * 你所使用的语言也许不支持队列。你可以使用 list 或者 deque（双端队列）来模拟一个队列, 只要是标准的队列操作即可。
 * 你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。
 */
public class Lc225 {

    class MyStack {

        private Deque<Integer> queue;

        private Integer top;

        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            queue = new LinkedList<>();
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            top = x;
            queue.offerLast(top);
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            if (queue.size() == 1) {
                top = null;
                return queue.removeFirst();
            }

            int size = queue.size() - 2;
            for (int i = 0; i < size; i++) {
                queue.offerLast(queue.removeFirst());
            }

            top = queue.removeFirst();
            queue.offerLast(top);

            return queue.removeFirst();
        }

        /**
         * Get the top element.
         */
        public int top() {
            return top;
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return top == null;
        }
    }
}
