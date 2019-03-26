package com.diwayou.acm.stack;

import com.google.common.collect.Lists;

import java.util.Stack;

/**
 * 仅用递归和栈操作逆序一个栈
 */
public class StackReverse {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        for (int i : Lists.newArrayList(1, 2, 3, 4, 5)) {
            stack.push(i);
        }

        System.out.println(stack);

        reverse(stack);

        System.out.println(stack);
    }

    private static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }

        int curBottom = getAndRemoveBottom(stack);
        reverse(stack);
        stack.push(curBottom);
    }

    private static Integer getAndRemoveBottom(Stack<Integer> stack) {
        Integer top = stack.pop();
        if (stack.isEmpty()) {
            return top;
        } else {
            Integer bottom = getAndRemoveBottom(stack);
            stack.push(top);

            return bottom;
        }
    }
}
