package com.diwayou.acm.coupon;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class SubsetTest {
    public static void main(String[] args) {
        List<Integer> data = List.of(1, 2, 3);
        List<List<Integer>> result = subsets(data);

        result.forEach(System.out::println);
        System.out.println();

        result = subsetRecursive(data);
        result.forEach(System.out::println);
    }

    private static List<List<Integer>> subsets(List<Integer> data) {
        if (data.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<Integer>> result = Lists.newArrayList();
        result.add(Collections.emptyList());
        for (Integer x : data) {
            List<List<Integer>> tmpResult = Lists.newArrayList();
            for (List<Integer> r : result) {
                List<Integer> tmp = Lists.newArrayListWithCapacity(r.size() + 1);
                tmp.addAll(r);
                tmp.add(x);
                tmpResult.add(tmp);
            }

            result.addAll(tmpResult);
        }

        return result;
    }

    private static List<List<Integer>> subsetRecursive(List<Integer> data) {
        List<List<Integer>> result = Lists.newArrayList();

        subsetsHelper(result, Lists.newArrayListWithCapacity(data.size()), data, 0);

        return result;
    }

    private static void subsetsHelper(List<List<Integer>> result, List<Integer> list, List<Integer> itemIds, int start) {

        result.add(Lists.newArrayList(list));

        for (int i = start; i < itemIds.size(); i++) {
            list.add(itemIds.get(i));

            subsetsHelper(result, list, itemIds, i + 1);

            list.remove(list.size() - 1);
        }
    }
}
