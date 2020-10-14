package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
 *
 * 给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表。
 * k是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 * 示例 :
 * 给定这个链表：1->2->3->4->5
 * 当k= 2 时，应当返回: 2->1->4->3->5
 * 当k= 3 时，应当返回: 3->2->1->4->5
 *
 * 说明 :
 * 你的算法只能使用常数的额外空间。
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 */
public class Lc25 {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;

        new Lc25().reverseKGroup(l1, 2);
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return head;
        }

        ListNode dummyHead = new ListNode(0), prev = dummyHead, node = head, t, tPrev;
        int i = k, cnt;
        while (head != null && i > 0) {
            head = head.next;
            i--;
        }

        if (i > 0) {
            return node;
        }

        i = k;
        cnt = 0;
        tPrev = node;
        while (i > 0) {
            t = prev.next;
            prev.next = node;
            node = node.next;
            prev.next.next = t;

            if (head != null) {
                head = head.next;
                cnt++;
            }
            i--;

            if (i == 0) {
                if (cnt >= k) {
                    i = k;
                    cnt = 0;
                    prev = tPrev;
                    tPrev = node;
                } else {
                    tPrev.next = node;
                }
            }
        }

        return dummyHead.next;
    }

    public ListNode reverseKGroup1(ListNode head, int k) {
        int count = 0;
        ListNode cur = head;
        while (cur != null && count < k) {
            cur = cur.next;
            count++;
        }

        if (count == k) {
            cur = reverseKGroup(cur, k);
            while (count > 0) {
                ListNode tmp = head.next;
                head.next = cur;
                cur = head;
                head = tmp;
                count--;
            }
            head = cur;
        }

        return head;
    }
}
