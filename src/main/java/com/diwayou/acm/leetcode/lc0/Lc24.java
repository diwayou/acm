package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 * <p>
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 */
public class Lc24 {

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // a -> b -> c -> d
        ListNode a, b, newHead, prev = null;
        a = head;
        b = head.next;
        newHead = b;
        while (b != null) {
            a.next = b.next;
            b.next = a;

            if (prev != null) {
                prev.next = b;
            }
            prev = a;

            a = a.next;
            if (a == null) {
                break;
            }
            b = a.next;
        }

        return newHead;
    }

    public ListNode swapPairs1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;

        return next;
    }
}
