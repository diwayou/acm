package com.diwayou.acm.team;

import java.util.Arrays;
import java.util.List;

/**
 * 大礼包
 * https://leetcode-cn.com/problems/shopping-offers/
 */
public class BigGiftBag2 {

    public static void main(String[] args) {
        List<Integer> price = Arrays.asList(3, 4);
        List<List<Integer>> special = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 2, 5));
        List<Integer> needs = Arrays.asList(2, 2);

        System.out.println(new BigGiftBag2().shoppingOffers(price, special, needs));
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
        boolean canUse;
        for (int[] bag : special) {
            canUse = check(bag, needs);

            // 回溯
            if (canUse) {
                for (int i = 0; i < needs.length; i++) {
                    if (bag[i] > 0) {
                        needs[i] -= bag[i];
                    }
                }

                cost = bag[bag.length - 1] + doShoppingOffers(price, special, needs);
                if (cost < minCost) {
                    minCost = cost;
                }

                for (int i = 0; i < needs.length; i++) {
                    if (bag[i] > 0) {
                        needs[i] += bag[i];
                    }
                }
            }
        }

        return minCost;
    }

    private static boolean check(int[] bag, int[] needs) {
        for (int i = 0; i < needs.length; i++) {
            if (needs[i] < bag[i]) {
                return false;
            }
        }

        return true;
    }
}
