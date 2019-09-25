package com.diwayou.acm.leetcode.lc100;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 */
public class Lc160 {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        int lenA = 0;
        int lenB = 0;
        ListNode node1 = headA;
        while (node1 != null) {
            lenA++;
            node1 = node1.next;
        }
        node1 = headB;
        while (node1 != null) {
            lenB++;
            node1 = node1.next;
        }

        ListNode node2;
        int diff;
        if (lenA > lenB) {
            node1 = headA;
            node2 = headB;
            diff = lenA - lenB;
        } else {
            node1 = headB;
            node2 = headA;
            diff = lenB - lenA;
        }

        while (diff-- > 0) {
            node1 = node1.next;
        }

        while (node1 != null && node2 != null) {
            if (node1 == node2) {
                return node1;
            }

            node1 = node1.next;
            node2 = node2.next;
        }

        return null;
    }

    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }

        return pA;
    }
}
