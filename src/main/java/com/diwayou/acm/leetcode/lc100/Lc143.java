package com.diwayou.acm.leetcode.lc100;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/reorder-list/
 * <p>
 * 给定一个单链表L：L0→L1→…→Ln-1→Ln ，
 * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
 * <p>
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 * 示例1:
 * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
 * <p>
 * 示例 2:
 * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
 */
public class Lc143 {

    private ListNode left;
    private boolean stop;

    public void reorderList(ListNode head) {
        left = head;
        stop = false;
        orderList(head);
    }

    //该方法作用是在指定条件下完成left指针和right指针的连接任务
    public void orderList(ListNode right) {
        if (right == null || this.stop) {
            return;
        }

        orderList(right.next);
        if (this.left == right || right.next == this.left) { //最后两指针相遇或错过时，left指针都指向尾结点
            left.next = null;   //防止出现环
            this.stop = true;   //stop为真时不做处理
        }

        if (!this.stop) {
            ListNode succ = this.left.next;
            right.next = succ;
            this.left.next = right;
            this.left = succ;
        }
    }

    public void reorderList1(ListNode head) {
        if (head == null) {
            return;
        }

        int count = 0;
        ListNode p = head;
        while (p != null) {
            p = p.next;
            count++;
        }
        count = (count + 1) / 2 - 1;
        p = head;
        while (count != 0) {
            p = p.next;
            count--;
        }

        ListNode p1 = p.next;
        p.next = null;
        while (p1 != null) {
            ListNode tmp = p1;
            p1 = p1.next;
            tmp.next = p.next;
            p.next = tmp;
        }

        p1 = p.next;
        p.next = null;
        p = head;
        while (p1 != null) {
            ListNode tmp = p1;
            p1 = p1.next;
            tmp.next = p.next;
            p.next = tmp;
            p = p.next.next;
        }
    }
}
