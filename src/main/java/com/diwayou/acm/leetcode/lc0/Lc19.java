package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 *
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 *
 * 示例：
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 *
 * 说明：
 * 给定的 n 保证是有效的。
 *
 * 进阶：
 * 你能尝试使用一趟扫描实现吗？
 */
public class Lc19 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        //head.next = new ListNode(2);

        head = new Lc19().removeNthFromEnd(head, 1);

        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;

        ListNode nBefore = dummyHead, node = head;
        while (n > 0 && node != null) {
            node = node.next;
            n--;
        }

        while (node != null) {
            node = node.next;
            nBefore = nBefore.next;
        }

        nBefore.next = nBefore.next.next;

        return dummyHead.next;
    }
}
