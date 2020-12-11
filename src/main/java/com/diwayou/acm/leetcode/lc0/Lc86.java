package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/partition-list/
 * <p>
 * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
 * <p>
 * 你应当保留两个分区中每个节点的初始相对位置。
 */
public class Lc86 {

    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode smaller = null;
        ListNode bigger = null;
        ListNode smallerHead = null;
        ListNode biggerHead = null;

        while (head != null) {
            if (head.val < x) {
                if (smaller == null) {
                    smaller = head;
                    smallerHead = smaller;
                } else {
                    smaller.next = head;
                    smaller = head;
                }
            } else {
                if (bigger == null) {
                    bigger = head;
                    biggerHead = bigger;
                } else {
                    bigger.next = head;
                    bigger = head;
                }
            }

            head = head.next;
        }

        if (smallerHead == null) {
            return biggerHead;
        }
        if (biggerHead == null) {
            return smallerHead;
        }

        smaller.next = biggerHead;
        bigger.next = null;

        return smallerHead;
    }
}
