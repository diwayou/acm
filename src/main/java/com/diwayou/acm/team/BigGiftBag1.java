package com.diwayou.acm.team;

import java.util.Arrays;
import java.util.List;

/**
 * 大礼包
 * https://leetcode-cn.com/problems/shopping-offers/
 */
public class BigGiftBag1 {

    public static void main(String[] args) {
        List<Integer> price = Arrays.asList(2, 5);
        List<List<Integer>> special = Arrays.asList(
                Arrays.asList(3, 0, 5),
                Arrays.asList(1, 2, 10));
        List<Integer> needs = Arrays.asList(3, 2);

        System.out.println(new BigGiftBag1().shoppingOffers(price, special, needs));
    }

    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int[] needsArr = toArray(needs);
        int[] priceArr = toArray(price);
        int[][] specialArr = new int[special.size()][];
        for (int i = 0; i < special.size(); i++) {
            specialArr[i] = toArray(special.get(i));
        }
        return doShoppingOffers(priceArr, specialArr, needsArr);
    }

    private static int[] toArray(List<Integer> l) {
        int[] arr = new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            arr[i] = l.get(i);
        }

        return arr;
    }

    public int doShoppingOffers(int[] price, int[][] special, int[] needs) {
        int minCost = 0;
        for (int i = 0; i < needs.length; i++) {
            if (needs[i] != 0) {
                minCost += price[i] * needs[i];
            }
        }
        if (minCost <= 0) {
            return minCost;
        }

        int cost;
        int[] remainNeeds;
        for (int[] bag : special) {
            remainNeeds = calcRemainNeeds(bag, needs);
            if (remainNeeds != null) {
                cost = bag[bag.length - 1] + doShoppingOffers(price, special, remainNeeds);
                if (cost < minCost) {
                    minCost = cost;
                }
            }
        }

        return minCost;
    }

    private static int[] calcRemainNeeds(int[] bag, int[] needs) {
        int[] remainNeeds = Arrays.copyOf(needs, needs.length);
        for (int i = 0; i < remainNeeds.length; i++) {
            if (remainNeeds[i] >= bag[i]) {
                remainNeeds[i] = needs[i] - bag[i];
            } else {
                return null;
            }
        }

        return remainNeeds;
    }
}
