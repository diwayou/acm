package com.diwayou.acm.leetcode.lc1100;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/relative-sort-array/
 * <p>
 * 给你两个数组，arr1 和arr2，
 * arr2中的元素各不相同
 * arr2 中的每个元素都出现在arr1中
 * 对 arr1中的元素进行排序，使 arr1 中项的相对顺序和arr2中的相对顺序相同。未在arr2中出现过的元素需要按照升序放在arr1的末尾。
 * <p>
 * 示例：
 * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
 * 输出：[2,2,2,1,4,3,3,9,6,7,19]
 * <p>
 * 提示：
 * arr1.length, arr2.length <= 1000
 * 0 <= arr1[i], arr2[i] <= 1000
 * arr2中的元素arr2[i]各不相同
 * arr2 中的每个元素arr2[i]都出现在arr1中
 */
public class Lc1122 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Lc1122().relativeSortArray(new int[]{2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19}, new int[]{2, 1, 4, 3, 9, 6})));
    }

    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        Map<Integer, Integer> map = new HashMap<>((int) ((float) arr2.length / 0.75F + 1.0F));
        for (int i = 0; i < arr2.length; i++) {
            map.put(arr2[i], i);
        }

        Integer[] a1 = new Integer[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            a1[i] = arr1[i];
        }

        Arrays.sort(a1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                Integer a = map.get(o1);
                if (a == null) {
                    a = 1001 + o1;
                }
                Integer b = map.get(o2);
                if (b == null) {
                    b = 1001 + o2;
                }

                return a.compareTo(b);
            }
        });

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = a1[i];
        }

        return arr1;
    }

    public int[] relativeSortArray1(int[] arr1, int[] arr2) {
        int[] cnt = new int[1001];
        for (int a1 : arr1) {
            cnt[a1]++;
        }
        int[] res = new int[arr1.length];
        int k = 0;
        for (int a2 : arr2) {
            int num = cnt[a2];
            while (num-- > 0) {
                res[k++] = a2;
            }
            cnt[a2] = 0;
        }
        for (int i = 0; i < 1001; i++) {
            if (k == arr1.length) {
                break;
            }
            while (cnt[i] > 0) {
                res[k++] = i;
                cnt[i]--;
            }
        }

        return res;
    }
}
