package com.diwayou.acm.leetcode.lc200;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/remove-linked-list-elements/
 * <p>
 * 删除链表中等于给定值 val 的所有节点。
 * <p>
 * 示例:
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 */
public class Lc203 {

    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode prev = dummyHead, next = head;
        while (next != null) {
            if (next.val == val) {
                prev.next = next.next;
            } else {
                prev = next;
            }

            next = next.next;
        }

        return dummyHead.next;
    }
}
