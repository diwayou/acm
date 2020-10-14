package com.diwayou.acm.leetcode.lc700;

import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/kth-largest-element-in-a-stream/
 *
 * 设计一个找到数据流中第K大元素的类（class）。注意是排序后的第K大元素，不是第K个不同的元素。
 * 你的KthLargest类需要一个同时接收整数k 和整数数组nums的构造器，它包含数据流中的初始元素。每次调用KthLargest.add，返回当前数据流中第K大的元素。
 *
 * 示例:
 * int k = 3;
 * int[] arr = [4,5,8,2];
 * KthLargest kthLargest = new KthLargest(3, arr);
 * kthLargest.add(3); // returns 4
 * kthLargest.add(5); // returns 5
 * kthLargest.add(10); // returns 5
 * kthLargest.add(9); // returns 8
 * kthLargest.add(4); // returns 8
 *
 * 说明:
 * 你可以假设nums的长度≥k-1且k ≥1。
 */
public class Lc703 {

    public static void main(String[] args) {
        int k = 3;
        int[] arr = {4, 5, 8, 2};
        KthLargest kthLargest = new KthLargest(3, arr);
        System.out.println(kthLargest.add(3));
        System.out.println(kthLargest.add(5));
        System.out.println(kthLargest.add(10));
        System.out.println(kthLargest.add(9));
        System.out.println(kthLargest.add(4));
    }

    private static class KthLargest {

        private int[] pq;

        private int N;

        private int cur;

        public KthLargest(int k, int[] nums) {
            pq = new int[k];
            N = k;

            for (int num : nums) {
                add(num);
            }
        }

        public int add(int val) {
            if (cur < N) {
                pq[cur++] = val;
                swim(cur);
            } else if (val > pq[0]) {
                pq[0] = val;
                sink(1);
            }

            return pq[0];
        }

        private void swap(int i, int j) {
            int t = pq[i - 1];
            pq[i - 1] = pq[j - 1];
            pq[j - 1] = t;
        }

        private void swim(int k) {
            while (k > 1 && greater(k / 2, k)) {
                swap(k, k / 2);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < N && greater(j, j + 1)) j++;
                if (!greater(k, j)) break;
                swap(k, j);
                k = j;
            }
        }

        private boolean greater(int i, int j) {
            return pq[i - 1] > pq[j - 1];
        }
    }

    class KthLargest1 {
        private PriorityQueue<Integer> pQueue;
        private int k;

        public KthLargest1(int k, int[] nums) {
            this.k = k;
            pQueue = new PriorityQueue<>(k);
            for (int n : nums) {
                add(n);
            }
        }

        public int add(int val) {
            if (pQueue.size() < k) {
                pQueue.offer(val);
            } else if (pQueue.peek() < val) {
                pQueue.poll();
                pQueue.offer(val);
            }

            return pQueue.peek();
        }
    }
}
