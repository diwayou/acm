package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 *
 * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 *
 * 示例 1:
 * 输入: 1->1->2
 * 输出: 1->2
 *
 * 示例 2:
 * 输入: 1->1->2->3->3
 * 输出: 1->2->3
 */
public class Lc83 {

    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummyHead = new ListNode(Integer.MIN_VALUE);
        dummyHead.next = head;

        ListNode node = head, prev = dummyHead;
        while (node != null) {
            if (node.val == prev.val) {
                prev.next = node.next;
            } else {
                prev = node;
            }

            node = node.next;
        }

        return dummyHead.next;
    }
}
