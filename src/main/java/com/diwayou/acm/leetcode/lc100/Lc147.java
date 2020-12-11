package com.diwayou.acm.leetcode.lc100;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/insertion-sort-list/
 * <p>
 * 对链表进行插入排序。
 * <p>
 * 插入排序的动画演示如上。从第一个元素开始，该链表可以被认为已经部分排序（用黑色表示）。
 * 每次迭代时，从输入数据中移除一个元素（用红色表示），并原地将其插入到已排好序的链表中。
 * <p>
 * 插入排序算法：
 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 重复直到所有输入数据插入完为止。
 * <p>
 * <p>
 * 示例 1：
 * 输入: 4->2->1->3
 * 输出: 1->2->3->4
 * <p>
 * 示例2：
 * 输入: -1->5->3->4->0
 * 输出: -1->0->3->4->5
 */
public class Lc147 {

    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummyHead = new ListNode(Integer.MIN_VALUE);
        ListNode node, t;
        while (head != null) {
            node = dummyHead;
            while (node.next != null && head.val > node.next.val) {
                node = node.next;
            }

            t = head;
            head = head.next;

            t.next = node.next;
            node.next = t;
        }

        return dummyHead.next;
    }

    public ListNode insertionSortList1(ListNode head) {
        ListNode dummyHead = new ListNode(Integer.MIN_VALUE), pre = dummyHead, tmp;
        dummyHead.next = head;
        while (pre.next != null) {
            if (pre.next.val < pre.val) {
                tmp = pre.next;
                pre.next = tmp.next;
                tmp.next = null;
                dummyHead.next = insertNode(dummyHead.next, tmp);
            } else
                pre = pre.next;
        }

        return dummyHead.next;
    }

    public ListNode insertNode(ListNode head, ListNode node) {
        if (head.val >= node.val) {
            node.next = head;
            return node;
        }
        ListNode pre = head;
        while (pre.next != null && pre.next.val < node.val) {
            pre = pre.next;
        }
        node.next = pre.next;
        pre.next = node;

        return head;
    }
}
