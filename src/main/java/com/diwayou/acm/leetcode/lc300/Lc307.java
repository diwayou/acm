package com.diwayou.acm.leetcode.lc300;

/**
 * https://leetcode-cn.com/problems/range-sum-query-mutable/
 *
 * 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。
 * update(i, val) 函数可以通过将下标为 i 的数值更新为 val，从而对数列进行修改。
 *
 * 示例:
 * Given nums = [1, 3, 5]
 * sumRange(0, 2) -> 9
 * update(1, 2)
 * sumRange(0, 2) -> 8
 *
 * 说明:
 * 数组仅可以在 update 函数下进行修改。
 * 你可以假设 update 函数与 sumRange 函数的调用次数是均匀分布的。
 */
public class Lc307 {

    class NumArray {

        BinaryIndexedTree binaryIndexedTree;
        int[] nums;

        public NumArray(int[] nums) {
            this.nums = nums;
            binaryIndexedTree = new BinaryIndexedTree(nums);
        }

        public void update(int i, int val) {
            binaryIndexedTree.update(i, val - nums[i]);
            nums[i] = val;
        }


        public int sumRange(int i, int j) {
            return binaryIndexedTree.rangeSum(i, j);
        }

    }

    class BinaryIndexedTree {
        private int[] bitArr;

        public BinaryIndexedTree(int[] list) {
            this.bitArr = new int[list.length + 1];
            for (int i = 0; i < list.length; i++) {
                this.update(i, list[i]);
            }
        }

        public void update(int idx, int delta) {
            idx += 1;
            while (idx < this.bitArr.length) {
                this.bitArr[idx] += delta;
                idx = idx + (idx & -idx);
            }
        }

        public int prefixSum(int idx) {
            idx += 1;
            int result = 0;
            while (idx > 0) {
                result += this.bitArr[idx];
                idx = idx - (idx & -idx);
            }

            return result;
        }

        public int rangeSum(int from_idx, int to_idx) {
            return prefixSum(to_idx) - prefixSum(from_idx - 1);
        }
    }
}
