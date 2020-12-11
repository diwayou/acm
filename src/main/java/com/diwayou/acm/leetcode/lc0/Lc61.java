package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/rotate-list/
 * <p>
 * 另外一个方法是快慢指针
 */
public class Lc61 {

    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null) {
            return head;
        }

        // 1 2 3 4 5
        // 2
        // 4 5 1 2 3
        ListNode node, tail = null;
        int len = 0;
        node = head;
        while (node != null) {
            len++;
            if (node.next == null) {
                tail = node;
            }
            node = node.next;
        }

        int oldHeadPosition = (k % len) + 1;
        if (oldHeadPosition == 1) {
            return head;
        }

        int newHeadPass = len - (oldHeadPosition - 1);

        node = tail;
        tail.next = head;
        for (int i = 0; i < newHeadPass; i++) {
            node = node.next;
        }

        ListNode newHead = node.next;
        node.next = null;

        return newHead;
    }
}
