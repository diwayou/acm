package com.diwayou.acm.leetcode.lc0;

import com.diwayou.acm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 */
public class Lc23 {

    public ListNode mergeKLists(ListNode[] lists) {
        int n = lists.length;
        for (int k = n / 2; k >= 1; k--)
            sink(lists, k, n);

        ListNode dummyHead = new ListNode(0);
        ListNode node = dummyHead;
        while (n > 0) {
            if (lists[0] == null) {
                swap(lists, 1, n--);

                if (n <= 0) {
                    break;
                }

                sink(lists, 1, n);
                continue;
            } else {
                sink(lists, 1, n);
            }

            node.next = lists[0];
            node = node.next;
            lists[0] = lists[0].next;
        }

        return dummyHead.next;
    }

    private static void sink(ListNode[] pq, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && !less(pq, j, j + 1)) j++;
            if (less(pq, k, j)) break;
            swap(pq, k, j);
            k = j;
        }
    }

    private static boolean less(ListNode[] pq, int i, int j) {
        if (pq[i - 1] == null) {
            return true;
        } else if (pq[j - 1] == null) {
            return false;
        }

        return pq[i - 1].val < pq[j - 1].val;
    }

    private static void swap(Object[] pq, int i, int j) {
        Object swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
    }

    public ListNode mergeKLists1(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        int end = lists.length;
        int start = 0;
        while (end > 0) {
            start = 0;
            for (int i = 0; i < end; ) {
                if (i + 1 >= end) {
                    lists[start] = lists[i];
                } else {
                    lists[start] = merge2Lists(lists[i], lists[i + 1]);
                }

                start++;
                i += 2;
            }
            if (end == 1) break;

            if (end % 2 == 0) {
                end = end / 2;
            } else {
                end = end / 2 + 1;
            }
        }

        return lists[0];
    }

    public ListNode merge2Lists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val <= l2.val) {
            l1.next = merge2Lists(l1.next, l2);

            return l1;
        } else {
            l2.next = merge2Lists(l1, l2.next);

            return l2;
        }
    }

    public static ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        return merge(lists, 0, lists.length - 1);
    }

    static ListNode merge(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }

        int mid = (right + left) / 2;
        ListNode l1 = merge(lists, left, mid);
        ListNode l2 = merge(lists, mid + 1, right);

        return mergeTwoLists(l1, l2);
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val <= l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }
}
