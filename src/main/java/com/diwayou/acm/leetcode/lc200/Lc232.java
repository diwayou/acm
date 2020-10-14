package com.diwayou.acm.leetcode.lc200;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode-cn.com/problems/implement-queue-using-stacks/
 *
 * 使用栈实现队列的下列操作：
 * push(x) -- 将一个元素放入队列的尾部。
 * pop() -- 从队列首部移除元素。
 * peek() -- 返回队列首部的元素。
 * empty() -- 返回队列是否为空。
 *
 * 示例:
 * MyQueue queue = new MyQueue();
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 *
 * 说明:
 * 你只能使用标准的栈操作 -- 也就是只有push to top,peek/pop from top,size, 和is empty操作是合法的。
 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 * 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。
 */
public class Lc232 {

    class MyQueue {

        private Deque<Integer> stackIn;
        private Deque<Integer> stackOut;

        /** Initialize your data structure here. */
        public MyQueue() {
            stackIn = new LinkedList<>();
            stackOut = new LinkedList<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            stackIn.offerLast(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            move();

            return stackOut.removeLast();
        }

        /** Get the front element. */
        public int peek() {
            move();

            return stackOut.getLast();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return stackIn.isEmpty() && stackOut.isEmpty();
        }

        private void move() {
            if (stackOut.isEmpty()) {
                while (!stackIn.isEmpty()) {
                    stackOut.offerLast(stackIn.removeLast());
                }
            }
        }
    }
}
