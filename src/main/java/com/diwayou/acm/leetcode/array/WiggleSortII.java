package com.diwayou.acm.leetcode.array;

// Given an unsorted array nums, reorder it in-place such that nums[0] < nums[1] > nums[2] < nums[3]....
// 给定一个无序的数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。

//示例 1:
//
//输入: nums = [1, 5, 1, 1, 6, 4]
//输出: 一个可能的答案是 [1, 4, 1, 5, 1, 6]
//示例 2:
//
//输入: nums = [1, 3, 2, 2, 3, 1]
//输出: 一个可能的答案是 [2, 3, 1, 3, 1, 2]
//
//来源：力扣（LeetCode）
//链接：https://leetcode-cn.com/problems/wiggle-sort-ii

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WiggleSortII {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 1, 1, 2, 2, 2};

        for (int i = 0; i < 1; i++) {
            shuffle(nums);
            wiggleSort(nums);
        }

        System.out.println(Arrays.toString(nums));
    }

    private static void shuffle(int[] nums) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = nums.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            swap(nums, index, i);
        }
    }

    public static void wiggleSort(int[] nums) {
        int indexMapSize = nums.length | 1;

        // Find a median.
        int mid = select(0, nums.length, nums.length / 2, nums);

        int i = 0, j = 0, k = nums.length - 1;
        int curIdxJ = indexMap(indexMapSize, j);
        int curIdxI = indexMap(indexMapSize, i);
        int curIdxK = indexMap(indexMapSize, k);
        int cur;
        while (j <= k) {
            cur = nums[curIdxJ];
            if (cur > mid) {
                //System.out.print(String.format("i%d -- j%d   ", i, j));
                swap(nums, curIdxI, curIdxJ);
                curIdxI = indexMap(indexMapSize, ++i);
                curIdxJ = indexMap(indexMapSize, ++j);
            } else if (cur < mid) {
                //System.out.print(String.format("j%d -- k%d   ", j, k));
                swap(nums, curIdxJ, curIdxK);
                curIdxK = indexMap(indexMapSize, --k);
            } else {
                curIdxJ = indexMap(indexMapSize, ++j);
            }
        }
    }

    private static int indexMap(int indexMapSize, int i) {
        return (1 + i + i) % indexMapSize;
    }

    private static void swap(int[] nums, int i, int j) {
        if (i == j || nums[i] >= nums[j]) {
            //System.out.println();
            return;
        }

        //System.out.println(String.format("%d[%d] <-> %d[%d]", i, nums[i], j, nums[j]));
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // copy from net.algart
    public static int select(
            final int from,
            final int to,
            final int requiredIndex,
            int[] array) {
        if (array == null) {
            throw new NullPointerException("Null array");
        }
        if (from < 0 || from >= to) {
            throw new IllegalArgumentException("Illegal from (" + from + ") or to (" + to
                    + ") arguments: must be 0 <= from < to");
        }
        int left = from;
        int right = to - 1;
        if (requiredIndex < left || requiredIndex > right) {
            throw new IllegalArgumentException("Index " + requiredIndex + " is out of range " + left + ".." + right);
        }
        // - measuring at real data shows that it is better to check min/max only for ALL array, not for sub-arrays
        for (; ; ) {
            if (requiredIndex == left) {
                return selectMin(left, right, array);
            }
            if (requiredIndex == right) {
                return selectMax(left, right, array);
            }
            assert requiredIndex > left;
            assert requiredIndex < right;
            final int difference = right - left;
            if (difference == 2) {
                final int base = left + 1;
                int a = array[left];
                int b = array[base];
                final int c = array[right];
                // Sorting a, b, c
                if ((b) < (a)) {
                    a = b;
                    b = array[left];
                }
                if ((c) < (b)) {
                    array[right] = b;
                    b = c;
                    // since this moment, c is not 3rd element
                    if ((b) < (a)) {
                        b = a;
                        a = c;
                    }
                }
                array[left] = a;
                array[base] = b;
                return array[requiredIndex];

            } else if (difference == 3) {
                final int afterLeft = left + 1;
                final int beforeRight = right - 1;
                int a = array[left];
                int b = array[afterLeft];
                int c = array[beforeRight];
                int d = array[right];
                // Sorting a, b, c, d
                if ((b) < (a)) {
                    a = b;
                    b = array[left];
                }
                if ((d) < (c)) {
                    d = c;
                    c = array[right];
                }
                // Now a <= b, c <= d
                if ((a) < (c)) {
                    // a..b, then c..d
                    array[left] = a;
                    if ((b) < (d)) {
                        array[right] = d;
                        if ((b) < (c)) {
                            array[afterLeft] = b;
                            array[beforeRight] = c;
                        } else {
                            array[afterLeft] = c;
                            array[beforeRight] = b;
                        }
                    } else {
                        // a <= c <= d <= b
                        array[afterLeft] = c;
                        array[beforeRight] = d;
                        array[right] = b;
                    }
                } else {
                    // c..d, then a..b
                    array[left] = c;
                    if ((d) < (b)) {
                        array[right] = b;
                        if ((d) < (a)) {
                            array[afterLeft] = d;
                            array[beforeRight] = a;
                        } else {
                            array[afterLeft] = a;
                            array[beforeRight] = d;
                        }
                    } else {
                        // c <= a <= b <= d
                        array[afterLeft] = a;
                        array[beforeRight] = b;
                        array[right] = d;
                    }
                }
                return array[requiredIndex];
            }

            // Switch above is better than common code below:
            // if (right - left < THRESHOLD) {
            //    sortLittleArray(left, right, array);
            //    return array[requiredIndex];
            // }

            int a = array[left];
            int c = array[right];
            // Sorting 3 elements to find a median from 3: left, (left+right)/2, right:
            int base = (left + right) >>> 1;
            // ">>>" will be correct even in the case of overflow
            int b = array[base];
            // Sorting a, b, c
            if ((b) < (a)) {
                a = b;
                b = array[left];
            }
            if ((c) < (b)) {
                array[right] = b;
                b = c;
                // since this moment, c is not 3rd element
                if ((b) < (a)) {
                    b = a;
                    a = c;
                }
            }
            array[left] = a;
            // array[base] = b; - virtually (really we can skip this operator)

            // Now data[left] <= data[base] <= data[right] (in other words, base is a median)
            // moving the base at the new position left+1
            int tmp = array[left + 1];
            array[left + 1] = b;
            array[base] = tmp;
            base = left + 1;
            assert b == array[base];

            // Reordering elements left+2..right-1 so that, for some K,
            //     data[left+2..K] <= data[base],
            //     data[K+1..right-1] >= data[base]
            int i = left + 1;
            int j = right;
            for (; ; ) {
                do
                    ++i;
                while ((array[i]) < (b));
                // Now
                //     data[left+2..i-1] <= data[base]
                //         (= data[base] for left+1,
                //         <= data[base] for left+2 and exchanged indexes,
                //         < data[base] for others),
                //     data[i] >= data[base],
                //     i <= j
                do
                    --j;
                while ((b) < (array[j]));
                // Now
                //     data[j] <= data[base],
                //     data[j+1..right-1] >= data[base],
                //     i <= j+1
                if (i >= j) {
                    break;
                }
                a = array[i];
                array[i] = array[j];
                array[j] = a;
                // Now
                //     data[left+1..i] <= data[base],
                //     data[j..right-1] >= data[base],
                //     i < j
            }
            // Now
            //     data[left+2..i-1] <= data[base],
            //     data[j] <= data[base],
            //     data[j+1..right-1] >= data[base],
            //     i >= j,
            // so
            //     data[left+2..j] <= data[base].
            // It means that elements are reordered and we can assign K=j
            array[base] = array[j];
            array[j] = b;
            // Now
            //     data[left..j-1] <= data[base],
            //     data[j] = data[base},
            //     data[j+1..right] >= data[base]
            assert left <= j;
            assert j <= right;
            if (requiredIndex == j) {
                return array[requiredIndex];
            } else if (requiredIndex < j) {
                right = j - 1;
            } else {
                left = j + 1;
            }
        }
    }

    private static int selectMin(int left, int right, int[] array) {
        int index = left;
        int result = array[left];
        for (int i = left + 1; i <= right; i++) {
            int v = array[i];
            if (v < result) {
                result = v;
                index = i;
            }
        }
        if (index != left) {
            array[index] = array[left];
            array[left] = result;
        }
        return result;
    }

    private static int selectMax(int left, int right, int[] array) {
        int index = right;
        int result = array[right];
        for (int i = right - 1; i >= left; i--) {
            int v = array[i];
            if (v > result) {
                result = v;
                index = i;
            }
        }
        if (index != right) {
            array[index] = array[right];
            array[right] = result;
        }
        return result;
    }
}
