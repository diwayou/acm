package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii/
 *
 * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中没有重复出现的数字。
 *
 * 示例1:
 * 输入: 1->2->3->3->4->4->5
 * 输出: 1->2->5
 *
 * 示例2:
 * 输入: 1->1->1->2->3
 * 输出: 2->3
 */
public class Lc82 {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummyHead = new ListNode(0);

        ListNode t, pre = head, node = dummyHead;
        t = pre;
        head = head.next;
        while (head != null) {
            if (head.val == pre.val) {
                pre = head;
                head = head.next;
            } else {
                if (pre == t) {
                    node.next = pre;
                    node = node.next;
                    node.next = null;
                }

                t = head;
                pre = head;
                head = head.next;
            }
        }

        if (pre == t) {
            node.next = pre;
            pre.next = null;
        }

        return dummyHead.next;
    }

    public ListNode deleteDuplicates1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode borrow = new ListNode(-1);
        borrow.next = head;

        ListNode slow = borrow;
        ListNode fast = head;
        while (fast != null) {
            if (fast.next == null || fast.next.val != fast.val) {
                if (slow.next == fast) {
                    slow = fast;
                } else {
                    slow.next = fast.next;
                }
            }

            fast = fast.next;
        }

        return borrow.next;
    }

    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode next = head.next;
        if (next.val == head.val) {
            while (next != null && next.val == head.val) {
                next = next.next;
            }
            return deleteDuplicates(next);
        } else {
            head.next = deleteDuplicates(head.next);
            return head;
        }
    }
}
