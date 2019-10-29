package com.diwayou.acm.leetcode.lc300;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/top-k-frequent-elements/
 *
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 *
 * 示例 2:
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *
 * 说明：
 * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 */
public class Lc347 {

    public static void main(String[] args) {
        System.out.println(new Lc347().topKFrequent(new int[]{-1,1,4,-4,3,5,4,-2,3,-1}, 3));
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            Integer v = cnt.get(num);
            if (v == null) {
                cnt.put(num, 1);
            } else {
                cnt.put(num, v + 1);
            }
        }

        Map.Entry<Integer, Integer>[] heap = new Map.Entry[k + 1];
        Iterator<Map.Entry<Integer, Integer>> iter = cnt.entrySet().iterator();
        for (int i = 0; i < k; i++) {
            heap[i + 1] = iter.next();
        }

        for (int i = k / 2; i >= 1; i--) {
            sink(heap, i);
        }

        Map.Entry<Integer, Integer> e;
        while (iter.hasNext()) {
            e = iter.next();
            if (e.getValue() > heap[1].getValue()) {
                heap[1] = e;
                sink(heap, 1);
            }
        }

        List<Integer> re = new ArrayList<>(k);
        for (int i = k - 1; i >= 0; i--) {
            re.add(heap[i + 1].getKey());
        }

        return re;
    }

    private static void sink(Map.Entry<Integer, Integer>[] heap, int k) {
        int n = heap.length - 1;
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && greater(heap, j, j + 1)) {
                j++;
            }
            if (!greater(heap, k, j)) {
                break;
            }

            exch(heap, k, j);
            k = j;
        }
    }

    private static boolean greater(Map.Entry<Integer, Integer>[] heap, int i, int j) {
        return heap[i].getValue().compareTo(heap[j].getValue()) > 0;
    }

    private static void exch(Map.Entry<Integer, Integer>[] heap, int i, int j) {
        Map.Entry<Integer, Integer> e = heap[i];
        heap[i] = heap[j];
        heap[j] = e;
    }

    public List<Integer> topKFrequent1(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>(nums.length);
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        List<Integer>[] buckets = new ArrayList[nums.length + 1];
        for (Integer key : map.keySet()) {
            int count = map.get(key);
            if (buckets[count] == null) {
                buckets[count] = new ArrayList<>();
            }
            buckets[count].add(key);
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && ans.size() < k; i--) {
            if (buckets[i] == null) continue;
            if (buckets[i].size() <= (k - ans.size())) {
                ans.addAll(buckets[i]);
            } else {
                ans.addAll(buckets[i].subList(0, k - ans.size()));
            }
        }

        return ans;
    }
}
