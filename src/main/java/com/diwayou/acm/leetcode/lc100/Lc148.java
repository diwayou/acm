package com.diwayou.acm.leetcode.lc100;

import com.diwayou.acm.leetcode.common.ListNode;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/sort-list/
 *
 * 在O(nlogn) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 *
 * 示例 1:
 * 输入: 4->2->1->3
 * 输出: 1->2->3->4
 *
 * 示例 2:
 * 输入: -1->5->3->4->0
 * 输出: -1->0->3->4->5
 */
public class Lc148 {

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode tmp = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(tmp);
        ListNode h = new ListNode(0);
        ListNode res = h;
        while (left != null && right != null) {
            if (left.val < right.val) {
                h.next = left;
                left = left.next;
            } else {
                h.next = right;
                right = right.next;
            }
            h = h.next;
        }
        h.next = left != null ? left : right;

        return res.next;
    }

    public ListNode sortList1(ListNode head) {
        int len = 0;
        ListNode t = head;
        while (t != null) {
            len++;
            t = t.next;
        }

        int[] arr = new int[len];
        t = head;
        len = 0;
        while (t != null) {
            arr[len++] = t.val;
            t = t.next;
        }

        Arrays.sort(arr);

        t = head;
        len = 0;
        while (t != null) {
            t.val = arr[len++];
            t = t.next;
        }

        return head;
    }
}
