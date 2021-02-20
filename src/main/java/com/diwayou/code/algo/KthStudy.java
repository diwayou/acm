package com.diwayou.code.algo;

import com.diwayou.acm.alg4.util.StdRandom;
import net.algart.arrays.ArraySelector;

public class KthStudy {
    public static void main(String[] args) {
        int n = 21;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }

        StdRandom.shuffle(arr);

        System.out.println(kth(arr, 0, arr.length, n / 2));

        System.out.println(ArraySelector.getQuickSelector().select(0, arr.length, n / 2, arr));
    }

    public static int kth(int[] arr, int from, int to, int k) {
        int lo = 0, hi = arr.length - 1;
        while (hi > lo) {
            int i = partition(arr, lo, hi);
            if (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return arr[i];
        }

        return arr[lo];
    }

    private static int partition(int[] arr, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        int v = arr[lo];
        while (true) {

            // find item on lo to swap
            while (arr[++i] < v)
                if (i == hi) break;

            // find item on hi to swap
            while (arr[--j] > v)
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(arr, i, j);
        }

        // put v = a[j] into position
        exch(arr, lo, j);

        // with a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    private static void exch(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
