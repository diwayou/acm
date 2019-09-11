package com.diwayou.team.bgb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 大礼包
 * https://leetcode-cn.com/problems/shopping-offers/
 */
public class BigGiftBag {

    public static void main(String[] args) {
        List<Integer> price = Arrays.asList(2, 5);
        List<List<Integer>> special = Arrays.asList(
                Arrays.asList(3, 0, 5),
                Arrays.asList(1, 2, 10));
        List<Integer> needs = Arrays.asList(3, 2);

        System.out.println(new BigGiftBag().shoppingOffers(price, special, needs));
    }

    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int minCost = calcCost(price, needs);
        if (minCost <= 0) {
            return minCost;
        }

        int cost;
        List<Integer> remainNeeds;
        for (List<Integer> bag : special) {
            remainNeeds = calcRemainNeeds(bag, needs);
            if (!remainNeeds.isEmpty()) {
                cost = bag.get(bag.size() - 1) + shoppingOffers(price, special, remainNeeds);
                if (cost < minCost) {
                    minCost = cost;
                }
            }
        }

        return minCost;
    }

    private static int calcCost(List<Integer> price, List<Integer> needs) {
        int cost = 0;
        for (int i = 0; i < needs.size(); i++) {
            if (needs.get(i) != 0) {
                cost += price.get(i) * needs.get(i);
            }
        }

        return cost;
    }

    private static List<Integer> calcRemainNeeds(List<Integer> bag, List<Integer> needs) {
        List<Integer> remainNeeds = new ArrayList<>(needs);
        for (int i = 0; i < remainNeeds.size(); i++) {
            if (remainNeeds.get(i) >= bag.get(i)) {
                remainNeeds.set(i, needs.get(i) - bag.get(i));
            } else {
                return Collections.emptyList();
            }
        }

        return remainNeeds;
    }
}
