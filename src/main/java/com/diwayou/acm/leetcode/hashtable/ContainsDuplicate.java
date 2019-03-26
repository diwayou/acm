package com.diwayou.acm.leetcode.hashtable;

//Given an array of integers, find if the array contains any duplicates. Your function should return
//true if any value appears at least twice in the array, and it should return false if every element is distinct.

import java.util.HashSet;
import java.util.Set;

class ContainsDuplicate {
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> map = new HashSet<>();
        for (int i : nums) {
            if (map.contains(i)) {
                return true;
            } else {
                map.add(i);
            }
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[] {1, 2, 3}));
        System.out.println(containsDuplicate(new int[] {1, 2, 2, 3}));
        System.out.println(containsDuplicate(new int[] {1, 2, 3, 2}));
    }
}
