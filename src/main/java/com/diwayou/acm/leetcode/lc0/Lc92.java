package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list-ii/
 *
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
 *
 * 说明:
 * 1 ≤m≤n≤ 链表长度。
 *
 * 示例:
 * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
 * 输出: 1->4->3->2->5->NULL
 */
public class Lc92 {

    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;

        ListNode pre = dummyHead;
        int i = 1;
        while (i < m) {
            head = head.next;
            pre = pre.next;
            i++;
        }

        ListNode cur, node = head;
        while (i <= n) {
            cur = head.next;
            head.next = pre.next;
            pre.next = head;

            head = cur;

            i++;
        }

        if (node != null) {
            node.next = head;
        }

        return dummyHead.next;
    }
}
